<template>
    <div>
        <a-layout>
            <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
                <p>
                    <a-form layout="inline" v-model="param">
                        <a-form-item>
                            <a-input v-model:value="param.name" placeholder="请输入名称" />
                        </a-form-item>
                        <a-form-item>
                            <a-button type="primary" @click="handleQuery({page:1,size:pagination.pageSize})">
                                查询
                            </a-button>
                        </a-form-item>
                      <a-form-item>
                        <a-button type="primary" @click="add()">
                          添加(后端测试使用)
                        </a-button>
                      </a-form-item>
                    </a-form>
                </p>
                
                <a-table :columns="columns" :row-key="record => record.id" :data-source="users"
                    :pagination="pagination" :loading="loading" @change="handleTableChange">
                  <template #avatar="{ text : avatar }">
                    <img  :src="avatar" alt="avatar">
                  </template>
                    <template v-slot:status="{text,record}" >
                      <a-switch checked-children="正常" un-checked-children="封禁" @change="changeState(record.id)" v-model:checked="record.status" />
                    </template>

                    <template #action="{ record }">
                        <a-space size="small">
                            <a-button type="primary" @click="edit(record)">
                                更新
                            </a-button>
                            <a-popconfirm title="确认要永久删除吗?" ok-text="确定" cancel-text="取消"
                                @confirm="handleDelete(record.id)">
                                <a-button type="danger">
                                    删除
                                </a-button>
                            </a-popconfirm>
                        </a-space>
                    </template>
                </a-table>
            </a-layout-content>
        </a-layout>
        <a-modal title="资源表单" v-model:visible="modalVisible" :confirm-loading="modalLoading" @ok="handleModalOk"
            @cancel="handleCancel">
            <a-form v-model="user" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
                <a-form-item label="登录名">
                    <a-input v-model:value="user.loginName" :disabled="user.id"/>
                </a-form-item>
              <a-form-item label="昵称">
                <a-input v-model:value="user.name" />
              </a-form-item>
              <a-form-item label="密码" v-show="!user.password">
                <a-input-password v-model:value="user.password"  />
              </a-form-item>
            </a-form>
        </a-modal>
    </div>
</template>

<script lang="ts">
import {defineComponent,reactive,toRefs,onMounted,ref} from "vue";
import axios from "axios";
import { message } from 'ant-design-vue'
import {Tool} from "@/util/tool";
declare let hexMd5:any
declare let KEY:any
export default defineComponent({
  name:'Admin-Ebook',
  setup(){
    const checked = ref<boolean>(false);
    const param = ref()
    param.value = {}
    const users = ref()
    const pagination = ref({
      current:1,
      pageSize:10,
      total:0
    })
    const loading = ref(false)
    const columns = [

      {
        title:'登录名',
        dataIndex:'loginName',
        slots: { customRender: 'loginName' }
      },
      {
        title:'昵称',
        dataIndex: 'name'
      },
      {
        title: '状态',
        dataIndex: 'status',
        slots: { customRender: 'status' }
      },
      {
        title:'头像',
        dataIndex:'avatar',
        slots: { customRender: 'avatar' }
      },
      {
        title:'注册时间',
        dataIndex: 'createTime'
      },
      {
        title:'操作',
        dataIndex: 'action',
        slots: {customRender: 'action'}
      }
    ];
    /**
     * 数据查询
     */
    const handleQuery = (params: any)=>{
      loading.value = true
      //如果不清楚现有数据，则编辑保存重新加载数据
      //ebook.value = []
      axios.get('/user/list',{
        params:{
          page: params.page,
          size: params.size,
          name: param.value.name
        }
      }).then(res =>{
        loading.value = false
        const data = res.data.data
        if (res.data.success){
          users.value = data.list
          //重置分页按钮
          pagination.value.current = params.page
          pagination.value.total = res.data.data.total
        }else {
          message.error(res.data.message)
        }

      })
    }
    /**
     * 表格点击页码时出发
     */
    const handleTableChange = (pagination: any)=>{
      console.log('看看自带的分页参数'+pagination)
      handleQuery({
        page:pagination.current,
        size:pagination.pageSize
      })
    }

    //表单
    const user = ref()
    const modalVisible = ref(false)
    const modalLoading = ref(false)
    const handleModalOk = () =>{
      modalLoading.value = true

      user.value.password = hexMd5(user.value.passowrd+ KEY )

      axios.post('/user/saveOrUpdate',user.value).then(res =>{
        const data = res.data
        if (data.success){
          modalVisible.value = false
          modalLoading.value = false
          message.success(res.data.message)
          //重新加载列表
          handleQuery({
            page:pagination.value.current,
            size: pagination.value.pageSize
          })
        }else {
          message.error(res.data.message)
        }
      })
    }
    const handleCancel = ()=>{
      modalVisible.value = false
    }
    //编辑
    const edit = (record:any)=>{
      modalVisible.value = true
      user.value = Tool.copy(record)
    }

    //编辑
    const add = ()=>{
      modalVisible.value = true
      user.value = {}
    }

    onMounted(()=>{
      handleQuery({
        page:pagination.value.current,
        size: pagination.value.pageSize
      })
    })

    const handleDelete = (id:String)=>{
      axios.delete('/user/delete/'+id).then(res =>{
        if (res.data.success){
          //重新加载列表
          handleQuery({
            page:pagination.value.current,
            size: pagination.value.pageSize
          })
        }
      })
    }
    const changeState = (id:String)=>{
      axios.get('/user/state/'+id).then(res=>{
        if (res.data.success){
          message.success(res.data.message)
        }
      })
    }
    return {
      changeState,
      checked,
      param,
      users,
      pagination,
      columns,
      loading,
      handleTableChange,

      edit,
      add,
      handleDelete,
      user,
      modalVisible,
      modalLoading,
      handleModalOk,
      handleCancel,
      handleQuery,
    }
  }
})
</script>
<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>
