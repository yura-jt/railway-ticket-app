<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>My Awesome Login Page</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
  <style>
    <%@ include file="/main.css" %>
  </style>
</head>
<body>
<div class="container h-100">
  <div class="d-flex justify-content-center h-100">
    <div class="card-header">
      <h3>Railway booking ticket app</h3>
    </div>
    <div class="user_card">
      <div class="d-flex justify-content-center">

        <div class="brand_logo_container">
          <img src="https://images.vexels.com/media/users/3/137010/isolated/preview/e9d76cc17bd895579486b1b9ad053444-train-circle-icon-by-vexels.png" class="brand_logo" alt="Logo">
        </div>
      </div>
      <div class="d-flex justify-content-center form_container">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-append">
              <span class="input-group-text"><i class="fas fa-user"></i></span>
            </div>
            <input type="text" name="" class="form-control input_user" value="" placeholder="username">
          </div>
          <div class="input-group mb-2">
            <div class="input-group-append">
              <span class="input-group-text"><i class="fas fa-key"></i></span>
            </div>
            <input type="password" name="" class="form-control input_pass" value="" placeholder="password">
          </div>
          <div class="form-group">

          </div>
          <div class="d-flex justify-content-center mt-3 login_container">
            <button type="button" name="button" class="btn login_btn">Login</button>
          </div>
        </form>
      </div>

      <div class="mt-4">
        <div class="d-flex justify-content-center links">
          Don't have an account? <a href="#" class="ml-2">Sign Up</a>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- YOUR CODE HERE -->
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>