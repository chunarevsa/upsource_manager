<#import "parts/common.ftlh" as common>
<#import "parts/login.ftlh" as li>

<@common.page>
    <div class="mb-3">Добро пожаловать!</div>

    <@li.login "/login" />
</@common.page>