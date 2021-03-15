<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<form id="userAddForm" class="userForm" method="post">
<table cellpadding="5" style="margin-left: 30px" >

    <tr>
        <td><span>新增用户</span></td>
    </tr>
    <tr>
        <td>用户名:</td>
        <td><input class="easyui-textbox" type="text" name="username" data-options="required:true" style="width: 280px;"/></td>
    </tr>
    <tr>
        <td>密码:</td>
        <td><input class="easyui-textbox" type="text" name="password" data-options="required:true" style="width: 280px;"/></td>
    </tr>
    <tr>
        <td>电话:</td>
        <td><input class="easyui-textbox" type="text" name="phone" data-options="required:true" style="width: 280px;"/></td>
    </tr>
    <tr>
        <td>邮箱:</td>
        <td><input class="easyui-textbox" type="text" name="email" data-options="required:false" style="width: 280px;"/></td>
    </tr>
    <tr>
        <td></td>
        <td>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
        </td>
    </tr>
</table>
</form>
<script style="text:javascript">
    //提交表单
    function submitForm(){
        //有效性验证
        if(!$('#userAddForm').form('validate')){
            $.messager.alert('提示','表单还未填写完成!');
            return ;
        }
        //ajax的post方式提交表单
        //$("#itemAddForm").serialize()将表单序列化为key-value形式的字符串
        //alert($("#itemAddForm").serialize());
        $.post("/user/add",$("#userAddForm").serialize(), function(data){
            if(data.status == 200){
                $.messager.alert('提示','新增用户成功!');
            }
            if(data.status === 501){
                $.messager.alert('提示',"用户名重复");
            }
        });
    }
</script>