<%--
  Created by IntelliJ IDEA.
  User: 李红义
  Date: 2021/3/10
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="UserList" title="用户列表"
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/user/list',method:'get',pageSize:30,toolbar:userListToolbar">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'id',width:60">ID</th>
        <th data-options="field:'username',width:80">用户名</th>
        <th data-options="field:'phone',width:80">电话</th>
        <th data-options="field:'email',width:80">邮箱</th>
        <th data-options="field:'created',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
<%--        <th data-options="field:'updated',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>--%>
    </tr>
    </thead>
</table>
<script>
/*    function formatItemParamData(value , index){
        var json = JSON.parse(value);
        var array = [];
        $.each(json,function(i,e){
            array.push(e.group);
        });
        return array.join(",");
    }*/

    function getSelectionsIds(){
        var userList = $("#UserList");
        var sels = userList.datagrid("getSelections");
        var ids = [];
        for(var i in sels){
            ids.push(sels[i].id);
        }
        ids = ids.join(",");
        return ids;
    }
    /* 工具选项窗口 */
    var userListToolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
            TAOTAO.createWindow({
                url : "/user-add",    /*通过controller,转到对应页面*/
            });
        }
    }/*,{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	$.messager.alert('提示','该功能未实现!');
        }
    }*/,{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
            var ids = getSelectionsIds();
            if(ids.length == 0){
                $.messager.alert('提示','未选中用户!');
                return ;
            }
            $.messager.confirm('确认','确定删除ID为 '+ids+' 的用户吗？',function(r){
                if (r){
                    var params = {"ids":ids};
                    $.post("/user/delete",params, function(data){
                        if(data.status == 200){
                            $.messager.alert('提示','删除用户成功!',undefined,function(){
                                $("#UserList").datagrid("reload");
                            });
                        }
                    });
                }
            });
        }
    }];
</script>
