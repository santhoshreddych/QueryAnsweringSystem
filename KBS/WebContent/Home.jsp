<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query Input</title>
<script type="text/javascript" 
                  src="Scripts/common.js"></script>
 <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> 
<link rel="stylesheet" type="text/css" href="Scripts/common.css" media="screen" />
</head>

<div id="header">
Flexible Query Answering System(ITCS - 6155 Summer 2013)
</div>




<form action="queryaanswer" method="post" onsubmit="">
<div id="signin" class="signin-box">

<label>
<div class="section_title">
Please Enter the Input Query
</div>
</label>
<textarea name="query"> 
</textarea>
<br>
<br>

<hr/>

<div class="section_title_special">
Sample Query
</div>
<div class="uname-label" style="font-family: calibri;line-height: 32px">
Sample code number > 1056784 ^ <br> 
Clump Thickness > 6 ^ <br>
Uniformity of Cell Size > 5 ^ <br> 
Uniformity of Cell Shape > 7 <br>
</div>

<hr/>

<input type="submit" class="btn_red btn" id="submit_button" name="Signin" value="Submit"/>   
 
</div>
</form>
<label>

</label>
</body>
</html>