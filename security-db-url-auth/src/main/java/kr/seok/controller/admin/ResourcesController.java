package kr.seok.controller.admin;


import kr.seok.domain.dto.ResourcesDto;
import kr.seok.domain.entity.Resources;
import kr.seok.domain.entity.Role;
import kr.seok.domain.repository.RoleRepository;
import kr.seok.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import kr.seok.service.ResourcesService;
import kr.seok.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ResourcesController {

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleService roleService;

//	@Autowired
//	private MethodSecurityService methodSecurityService;

	@Autowired
	private UrlFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

	@GetMapping(value="/admin/resources")
	public String getResources(Model model) {

		List<Resources> resources = resourcesService.getResources();
		model.addAttribute("resources", resources);

		return "admin/resource/list";
	}

	@PostMapping(value="/admin/resources")
	public String createResources(ResourcesDto resourcesDto) {

		ModelMapper modelMapper = new ModelMapper();
		Role role = roleRepository.findByRoleName(resourcesDto.getRoleName());
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		Resources resources = modelMapper.map(resourcesDto, Resources.class);
		resources.setRoleSet(roles);

		resourcesService.createResources(resources);

		if("url".equals(resourcesDto.getResourceType())){
			/* URL 권한 정보 입력시 reload */
			filterInvocationSecurityMetadataSource.reload();
		}else{
//			methodSecurityService.addMethodSecured(resourcesDto.getResourceName(),resourcesDto.getRoleName());
		}

		return "redirect:/admin/resources";
	}

	@GetMapping(value="/admin/resources/register")
	public String viewRoles(Model model) {

		List<Role> roleList = roleService.getRoles();
		model.addAttribute("roleList", roleList);

		ResourcesDto resources = new ResourcesDto();
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(new Role());
		resources.setRoleSet(roleSet);
		model.addAttribute("resources", resources);

		return "admin/resource/detail";
	}

	@GetMapping(value="/admin/resources/{id}")
	public String getResources(@PathVariable String id, Model model) {

		List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);
		Resources resources = resourcesService.getResources(Long.parseLong(id));

		ModelMapper modelMapper = new ModelMapper();
		ResourcesDto resourcesDto = modelMapper.map(resources, ResourcesDto.class);
		model.addAttribute("resources", resourcesDto);

		return "admin/resource/detail";
	}

	@GetMapping(value="/admin/resources/delete/{id}")
	public String removeResources(@PathVariable String id) {

		Resources resources = resourcesService.getResources(Long.parseLong(id));
		resourcesService.deleteResources(Long.parseLong(id));

		if("url".equals(resources.getResourceType())) {
			filterInvocationSecurityMetadataSource.reload();
		}else{
//			methodSecurityService.removeMethodSecured(resources.getResourceName());
		}

		return "redirect:/admin/resources";
	}
}
