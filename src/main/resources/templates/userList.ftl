<#import "parts/common.ftl" as c>
<@c.page>

    <h3>List of users:</h3>
    <table xmlns="http://www.w3.org/1999/html" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th>Options</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role> ${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}" class="btn btn-secondary btn-sm active"
                       role="button" aria-pressed="true">Edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>