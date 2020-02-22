<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    <h2>User editor</h2>

    <form action="/user" method="post" class="form-inline">
        <label for="user">Username</label>
        <div class="custom-control my-1 mr-sm-2">
            <input type="text" class="form-control" value="${user.username}" name="username" id="user">
        </div>

        <#list roles as role>
            <div class="custom-control custom-checkbox my-1 mr-sm-2">
                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input"
                           name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                    ${role}
                </label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-primary">Save</button>

    </form>

</@c.page>