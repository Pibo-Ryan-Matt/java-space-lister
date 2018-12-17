<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Your Profile" />
    </jsp:include>
    <jsp:include page="/WEB-INF/partials/scripts.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />
<div class="uk-container">
    <div class="uk-text-center uk-grid">
        <div class="uk-width-1-1">
            <div class="uk-card uk-card-default uk-card-body">
                <form action="/edit_profile" method="POST">
                    <div class="uk-grid">
                        <div class="uk-width-1-3">
                            <input class="uk-input uk-form-width-medium" type="text" placeholder="Username">
                        </div>
                        <div class="uk-width-1-3">
                            <input class="uk-input uk-form-width-medium" type="text" placeholder="Email">
                        </div>
                        <div class="uk-width-1-3">
                            <input class="uk-input uk-form-width-medium" type="password" placeholder="Password">
                        </div>
                        <div class="uk-width-1-1">
                            <button class="uk-button uk-button-default uk-margin-small-top">Submit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
