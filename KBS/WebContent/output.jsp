<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result</title>
<link rel="shortcut icon" href="aero2.ico" >
<script type="text/javascript" 
                  src="Scripts/common.js"></script>
 <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> 
<link rel="stylesheet" type="text/css" href="Scripts/common.css" media="screen" />
</head>

<div id="header">
Flexible Query Answering System
</div>





<form action="queryaanswer" method="post" onsubmit="">
<div id="signin" class="signin-box">

<label>
<div class="section_title">
Relaxed Query
</div>
</label>
<textarea name="query" rows="6" cols="50"> 
<% out.println(request.getAttribute("relaxed_query"));  %>
</textarea>
<br>
<br/>
<label>
<div class="section_title_special">
Result
</div>
</label>
<textarea name="query" rows="15" cols="50"> 
<% out.println(request.getAttribute("result")); %>
</textarea>
<br><br>
<label>
<div class="section_title" style="background:#DA4F49">
Rules Generated
</div>
</label>
<textarea name="query" rows="15" cols="50"> 
<% out.println(request.getAttribute("rulesoutput")); %>
</textarea>
   
 
</div>
</form>
</body>
</html>