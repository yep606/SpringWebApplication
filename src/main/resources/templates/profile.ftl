<#import "parts/common.ftl" as c>

<@c.page>
<h5>${username}</h5>
    <form method="post">
        <div class="form-group">
            <div class="col-4">
                <label for="passLog"> Password: </label>
                <input type="password" class="form-control mb-2" id="passLog" name="password" placeholder="Password"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-4">
                <label for="emaillog"> Email: </label>
                <input type="email" class="form-control mb-2" id="emailLog" name="email"
                       placeholder="${email!''}"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>