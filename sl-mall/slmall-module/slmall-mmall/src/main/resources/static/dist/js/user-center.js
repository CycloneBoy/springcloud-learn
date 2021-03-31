webpackJsonp([6],{0:function(e,r,t){e.exports=t(71)},2:function(e,r,t){"use strict";var s=t(1),n={login:function(e,r,t){s.request({url:s.getServerUrl("/user/login.do"),data:e,method:"POST",success:r,error:t})},checkUsername:function(e,r,t){s.request({url:s.getServerUrl("/user/login.do"),data:{type:"username",str:e},method:"POST",success:r,error:t})},register:function(e,r,t){s.request({url:s.getServerUrl("/user/register.do"),data:e,method:"POST",success:r,error:t})},checkLogin:function(e,r){s.request({url:s.getServerUrl("/user/get_user_info.do"),method:"POST",success:e,error:r})},getUserInfo:function(e,r){s.request({url:s.getServerUrl("/user/get_information.do"),method:"POST",success:e,error:r})},updateUserInfo:function(e,r,t){s.request({url:s.getServerUrl("/user/update_information.do"),data:e,method:"POST",success:r,error:t})},getQuestion:function(e,r,t){s.request({url:s.getServerUrl("/user/forget_get_question.do"),data:{username:e},method:"POST",success:r,error:t})},checkAnswer:function(e,r,t){s.request({url:s.getServerUrl("/user/forget_check_answer.do"),data:e,method:"POST",success:r,error:t})},resetPassword:function(e,r,t){s.request({url:s.getServerUrl("/user/forget_reset_password.do"),data:e,method:"POST",success:r,error:t})},updatePassword:function(e,r,t){s.request({url:s.getServerUrl("/user/reset-password.do"),data:e,method:"POST",success:r,error:t})},logout:function(e,r){s.request({url:s.getServerUrl("/user/logout.do"),method:"POST",success:e,error:r})}};e.exports=n},3:function(e,r,t){"use strict";var s=t(1),n={getCartCount:function(e,r){s.request({url:s.getServerUrl("/cart/get_cart_product_count.do"),success:e,error:r})},addToCart:function(e,r,t){s.request({url:s.getServerUrl("/cart/add.do"),data:e,success:r,error:t})},getCartList:function(e,r){s.request({url:s.getServerUrl("/cart/list.do"),success:e,error:r})},selectProduct:function(e,r,t){s.request({url:s.getServerUrl("/cart/select.do"),data:{productId:e},success:r,error:t})},unSelectProduct:function(e,r,t){s.request({url:s.getServerUrl("/cart/un_select.do"),data:{productId:e},success:r,error:t})},selectAllProduct:function(e,r){s.request({url:s.getServerUrl("/cart/select_all.do"),success:e,error:r})},unSelectAllProduct:function(e,r){s.request({url:s.getServerUrl("/cart/un_select_all.do"),success:e,error:r})},updateProduct:function(e,r,t){s.request({url:s.getServerUrl("/cart/update.do"),data:e,success:r,error:t})},deleteProduct:function(e,r,t){s.request({url:s.getServerUrl("/cart/delete_product.do"),data:{productIds:e},success:r,error:t})}};e.exports=n},4:function(e,r){},5:function(e,r,t){"use strict";t(4);var s=t(1),n=t(2),o=t(3),c={init:function(){return this.bindEvent(),this.loadUserInfo(),this.loadCartCount(),this},bindEvent:function(){$(".js-login").click(function(){s.doLogin()}),$(".js-register").click(function(){window.location.href="./user-register.html"}),$(".js-logout").click(function(){n.logout(function(e){window.location.reload()},function(e){s.errorTips(e)})})},loadUserInfo:function(){n.checkLogin(function(e){$(".user.not-login").hide().siblings(".user.login").show().find(".username").text(e.username)},function(e){})},loadCartCount:function(){o.getCartCount(function(e){$(".nav .cart-count").text(e||0)},function(e){$(".nav .cart-count").text(0)})}};e.exports=c.init()},7:function(e,r){},8:function(e,r){e.exports='{{#navList}} {{#isActive}} <li class="nav-item active"> {{/isActive}} {{^isActive}} </li><li class="nav-item"> {{/isActive}} <a class="link" href="{{href}}">{{desc}}</a> </li> {{/navList}}'},9:function(e,r,t){"use strict";t(7);var s=t(1),n=t(8),o={option:{name:"",navList:[{name:"user-center",desc:"个人中心",href:"./user-center.html"},{name:"order-list",desc:"我的订单",href:"./order-list.html"},{name:"user-pass-update",desc:"修改密码",href:"./user-pass-update.html"},{name:"about",desc:"关于MMall",href:"./about.html"}]},init:function(e){$.extend(this.option,e),this.renderNav()},renderNav:function(){for(var e=0,r=this.option.navList.length;e<r;e++)this.option.navList[e].name===this.option.name&&(this.option.navList[e].isActive=!0);var t=s.renderHtml(n,{navList:this.option.navList});$(".nav-side").html(t)}};e.exports=o},33:function(e,r){},53:function(e,r){e.exports='<div class="user-info"> <div class="form-line"> <span class="label">用户名: </span> <span class="text">{{username}}</span> </div> <div class="form-line"> <span class="label">电话: </span> <span class="text">{{phone}}</span> </div> <div class="form-line"> <span class="label">邮箱: </span> <span class="text">{{email}}</span> </div> <div class="form-line"> <span class="label">问题: </span> <span class="text">{{question}}</span> </div> <div class="form-line"> <span class="label">答案: </span> <span class="text">{{answer}}</span> </div> <a class="btn btn-submit" href="./user-center-update.html">编辑</a> </div>'},71:function(e,r,t){"use strict";t(33),t(5),t(12),t(6);var s=t(9),n=t(1),o=t(2),c=t(53),u={init:function(){this.onLoad()},onLoad:function(){s.init({name:"user-center"}),this.loadOrderList()},loadOrderList:function(){var e="";o.getUserInfo(function(r){e=n.renderHtml(c,r),$(".panel-body").html(e)},function(e){n.errorTips(e)})}};$(function(){u.init()})}});