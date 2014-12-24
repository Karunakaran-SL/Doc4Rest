<html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/default.css" media="screen" rel="stylesheet" type="text/css">
    <link href="css/doc4rest.css" media="screen" rel="stylesheet" type="text/css">
    <title>REST API Services</title>
  </head>
  <body>
    <div class="container">
      <div class="main">
        <div class="whiteHeader">
          <div class="title">
            <div class="topLogo"> 
            <a href="${doc4rest.logoLink}" title="${doc4rest.logoName}" tabindex="1"><img src="${doc4rest.logoImageLink}"
                  alt="${doc4rest.logoName}"></a> </div>
            <h1>REST API Services</h1>
          </div>
        </div>
        <div width="100%" id="desc"> <br>
          <h3>The following web services are exposed as RESTful APIs: </h3>
          <br>
          <table class="smallfonttable" style="table-layout:fixed;"
            id="services" border="0" cellpadding="4" cellspacing="6"
            width="97%">
            <tbody>
              <tr bgcolor="#ccccc" valign="top">
                <th align="left" width="30%">Service Name</th>
                <th align="left" width="30%">Service Path</th>
                <th align="left" width="40%">Description</th>
              </tr>
              <#list services as service>
				<tr valign="top">
                   <td><a href="${service.link4Index}"><b>${service.serviceName}</b></a></td>
                    <td><a href="${service.link4Index}"><b>${service.path}</b></a></td>
                    <td>
                    <p>${service.desc}</p>
                </td>
              	</tr>
    		  </#list>
            </tbody>
          </table>
        </div>
      </div>
      <div class="bottomLinks" align="center">
        <div class="moz-text-html" lang="x-western">
          <div id="bottomLine">
            <div id="copyrightText">Generated Using Doc4Rest Eclipse Plugin</div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>