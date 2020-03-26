<#-- @ftlvariable name="data" type="com.jetbrains.handson.website.IndexData" -->
<html>
<title>
        FreeMarker
</title>
<body>
<h1>FreeMarker</h1>
<p>
        Using FreeMarker Template Engine
</p>
<ul>
    <#list data.items as item>
        <li>${item}</li>
    </#list>
</ul>
</body>
</html>