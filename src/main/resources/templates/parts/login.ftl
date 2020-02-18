<#macro login path isRegisterForm>

    <form action="${path}" method="post">
        <div class="form-group">
            <div class="col-4">
                <label for="usrlog"> User Name :</label>
                <input type="text" class="form-control mb-2" id="usrlog" name="username" placeholder="Username"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-4">
                <label for="passlog"> Password: </label>
                <input type="password" class="form-control mb-2" id="passlog" name="password" placeholder="Password"/>
            </div>
        </div>
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