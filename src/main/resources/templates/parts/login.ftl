<#macro login path isRegisterForm>

    <form action="${path}" method="post">
        <div class="form-group">
            <div class="col-4">
                <label for="usrLog"> User Name :</label>
                <input type="text" class="form-control mb-2 ${(usernameError??)?string('is-invalid', '')}" id="usrLog"
                       name="username" value="<#if user??>${user.username}</#if>" placeholder="Username"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group">
            <div class="col-4">
                <label for="passLog"> Password: </label>
                <input type="password" class="form-control mb-2 ${(passwordError??)?string('is-invalid', '')}"
                       id="passLog" name="password" placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group">
                <div class="col-4">
                    <label for="passLog"> Password: </label>
                    <input type="password"
                           class="form-control mb-2 ${(passwordConfirmError??)?string('is-invalid', '')}"
                           id="passLog" name="passwordConfirm" placeholder="Retype password"/>
                    <#if passwordConfirmError??>
                        <div class="invalid-feedback">
                            ${passwordConfirmError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group">
                <div class="col-4">
                    <label for="emaillog"> Email: </label>
                    <input type="email" class="form-control mb-2 ${(emailError??)?string('is-invalid', '')}"
                           id="emailLog" value="<#if user??>${user.email}</#if>" name="email"
                           placeholder="example@yahoo.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-4">
                <div class="g-recaptcha" data-sitekey="6LdiROEUAAAAACeL2qPE2ZshZFgbC1vkQIGJHKOn"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm>
            <a href="/registration">Sign up</a>
        </#if>
        <button type="submit" class="btn btn-primary">
            <#if isRegisterForm>
                Create
            <#else>
                Sign in
            </#if>

        </button>
    </form>
</#macro>

<#macro logout>

    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Sign Out</button>
    </form>

</#macro>