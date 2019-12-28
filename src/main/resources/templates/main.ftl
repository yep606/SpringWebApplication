<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    <div>
        <@l.logout/>
    </div>
    <div>
        <form method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="text" name="name" placeholder="Введите сообщение"/>
            <input type="text" name="tag" placeholder="Тэг"/>
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Table messages</div>
    <form method="post" action="filter">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="text" name="filter">
        <button type="submit">Найти</button>
    </form>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.name} - имя</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </div>
    <#else>
        No message
    </#list>
</@c.page>