<#import "parts/common.ftl" as c>
<@c.page>

    <form method="get" action="/main">
        <div class="form-row">
            <div class="form-group col-md-6">
                <input type="text" name="filter" value="${filter!}" placeholder="Write tag here..."/>
                <button type="submit" class="btn btn-primary ml-2">Searching</button>
            </div>
        </div>
    </form>

    <a class="btn btn-primary" data-toggle="collapse" href="#messageCollapse" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new message
    </a>

    <div class="collapse" id="messageCollapse">
        <div class="mt-2">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control" name="name" placeholder="Введите сообщение"/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="tag" placeholder="Тэг"/>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
        </div>
    </div>

    <div class="card-columns">
    <#list messages as message>
        <div class="card" style="width: 18rem;">
            <div>
                <#if message.filename??>
                    <img class="card-img-top" src="/img/${message.filename}">
                </#if>
            </div>
            <div class="m-2">
                <span>${message.text}</span>
                <i>${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                ${message.authorName}
            </div>
        </div>
    <#else>
        No message
    </#list>
    <div>
</@c.page>