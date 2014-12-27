<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../../css/default.css" media="screen" rel="stylesheet" type="text/css">
<link href="../../css/doc4rest.css" media="screen" rel="stylesheet" type="text/css">
<title>REST API Services</title>
</head>
<body>
<div class="container">
<div class="main">
<div class="whiteHeader">
<div class="title">
<div class="topLogo">
<a href=${doc4rest.logoLink} title=${doc4rest.logoName} tabindex="1"><img src=${doc4rest.logoImageLink}
                  alt=${doc4rest.logoName}></a> </div>
		<h1><a href=${service.link4Api}> ${service.serviceName} API</a> </h1>
</div>
</div>
<div class="content">
<div class="item">
<h1>${api.path}</h1>
<p>The following operations are supported on this resource:</p>
<ul>
<#list operations as operation>
<li>
<b>${operation}</b>
</li>
</#list>
</ul>
</div>
<div class="item">
<h1>
${api.path}
</h1>
<br>
<p>${api.desc}</p>
<p>
<#if sampleMap?has_content>
<b>Example Usage</b>
</p>
<table class="smallfonttable" style="table-layout:fixed;"
            id="services" border="0" cellpadding="4" cellspacing="6"
            width="97%">
            <tbody>
              <#list sampleMap?keys as key>
				<tr valign="top">
                    <td><b>${key}</b></a></td>
                    <td><b>${sampleMap[key]} </b></a></td>
                    <td>
                </td>
              	</tr>
    		  </#list>
            </tbody>
</table>
</#if>
<br>
<#if attrMap?has_content>
<b>Rest Attributes</b>
<table class="smallfonttable" style="table-layout:fixed;"
            id="services" border="0" cellpadding="4" cellspacing="6"
            width="97%">
            <tbody>
              <#list attrMap?keys as key>
				<tr valign="top">
                    <td><b>${key}</b></a></td>
                    <td><b>${attrMap[key]} </b></a></td>
                    <td>
                </td>
              	</tr>
    		  </#list>
            </tbody>
 </table>
</#if>
<#if paramsMap?has_content>
<b>Parameter</b>
 <table class="smallfonttable" style="table-layout:fixed;"
            id="services" border="0" cellpadding="4" cellspacing="6"
            width="97%">
            <tbody>
              <tr bgcolor="#ccccc" valign="top">
                <th align="left" width="30%">Name</th>
                <th align="left" width="30%">Description</th>
                <th align="left" width="30%">Type</th>
                <th align="left" width="30%">Rest Type</th>
              </tr>
              <#list paramsMap?keys as key>
				<tr valign="top">
                   <td><b>${paramsMap[key].name}</b></a></td>
                    <td><b>${paramsMap[key].desc}</b></a></td>
                    <td><b>${paramsMap[key].type}</b></a></td>
                    <td><b>${paramsMap[key].annotation}</b></a></td>
                    <td>
                </td>
              	</tr>
    		  </#list>
            </tbody>
          </table>
</#if>
</div>
</div>
<div class="sidenav">
<h1>Home</h1>
<ul>
<li>
<a href="../../index.html">Introduction</a>
</li>
</ul>
<h1>REST Resources</h1>
<ul>
<#list allApi?keys as key>
<li>
<a href=${allApi[key].link4Api}>
<wbr></wbr>${key}</a>
</li>
</#list>
</div>
<div class="clearer">
<span></span>
</div>
</div>
<div class="footer" align="center">
<div class="moz-text-html" lang="x-western">
<div id="bottomLine">
<div id="copyrightText">Generated Using Doc4Rest Eclipse Plugin.</div>
</div>
</div>
</div>
</div>
</body>
</html>
