<?php $page_title = 'ثبت نام';
include ('includes/header.html');
?>
<h1> regidterrr </h1>
<?php
if (isset($_POST['submit'])) {
    $name = $_POST['name'];
    $username = $_POST['username'];
    $password = $_POST['password'];
    $email = $_POST['email'];
    if (!empty($name) && !empty($username) && !empty($password) && !empty($email)) {
        $respond = 'successful reg';
    } else {
        $respond = 'please fill all the fields';
    }
    echo $respond;
}
?>
<!-- کد برای ساخت فرم - form.html -->
<form action="darsname.php" method="post">
    <fieldset> <legend align="right">please fill this form </legend>
        <label> name kamel </label>
        <input type="text" name="name" />
        <label> شناسه </label> <input type="text" name="username" />
        <label> رمز </label>  <input type="text" name="password" />
        <label> رایانامه </label> <input type="text" name="email" />
    </fieldset>
    <input type="submit" name="submit" value="ارسال" class="submit"/>
</form>
<?php include ('includes/footer.html'); ?>