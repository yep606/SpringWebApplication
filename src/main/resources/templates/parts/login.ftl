<#macro login path isRegisterForm>

    <form action="${path}" method="post">
        <div class="form-group">
            <div class="col-4">
                <label for="usrLog"> User Name :</label>
                <input type="text" class="form-control mb-2" id="usrLog" name="username" placeholder="Username"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-4">
                <label for="passLog"> Password: </label>
                <input type="password" class="form-control mb-2" id="passLog" name="password" placeholder="Password"/>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group">
                <div class="col-4">
                    <label for="emaillog"> Email: </label>
                    <input type="email" class="form-control mb-2" id="emailLog" name="email"
                           placeholder="example@yahoo.com"/>
                </div>
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