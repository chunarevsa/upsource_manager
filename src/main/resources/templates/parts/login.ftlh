<#macro login path>

<form action="${path}" method="post">

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <div class="col-sm-3">
            <input type="text" name="username" class="form-control" placeholder="User name" />
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-3">
            <input type="password" name="password" class="form-control" placeholder="Password" />
        </div>
    </div>
    <button class="btn btn-primary" type="submit">Sign In</button>
</form>
</#macro>