## FormLogin Sample Project

### REST API

|NO|URI|METHOD|AUTH|DESC|
|:---:|:---|:---|:---|:---|
|1|'/'|GET|ALL|기본 페이지 이동|
|2|'/users'|GET|ALL|회원가입 페이지 이동|
|3|'/users'|POST|ALL|회원가입 요청|
|4|'/login'|GET|ALL|로그인 페이지 이동|
|5|'/logout'|GET|Authentication|로그아웃 요청|
|6|'/denied'|GET|?|접근거부 응답 페이지|
|7|'/mypage'|GET|USER|마이페이지 이동|
|8|'/messages'|GET|MANAGER|권한이 필요한 메시지 페이지 이동|
|9|'/config'|GET|ADMIN|권한이 필요한 환경설정 페이지 이동|

### View
- 폴더 구조
```
resources
    templates
        layout
            footer.html
            header.html
            top.html
        user
            login
                denied.html
                register.html
            messages.html
            mypage.html
        home.html
        login.html
```

### BackEnd Filter
- 폴더 구조

```
src
    main
        java
            seok
                FormAuthApplication
                controller
                    admin
                        AdminController
                        ConfigController
                    login
                        LoginController
                    user
                        MessageController
                        UserController
                    HomeController
                domain
                    UserRepository
                    Account
                    AccountDto
                service
                    UserService
                    UserServiceImpl
                /* 스프링 시큐리티 폴더 구조 */
                security
                    common
                        FormAuthenticationDetailsSource
                        FormWebAuthenticationDetails
                    config
                        SecurityConfig
                    handler
                        CustomAccessDeniedHandler
                        CustomAuthenticationFailureHandler
                        CustomAuthenticationSuccessHandler
                    provider
                        CustomAuthenticationProvider
                    service
                        AccountContext
                        CustomUserDetailsService 
```
