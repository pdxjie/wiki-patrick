<template>

  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
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
              新增
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="categorys"
        :pagination="pagination"
        :loading="loading"
        @change="handleTableChange"
      >
        <template #cover="{text:cover}">
          <img v-if="cover" :src="cover" alt="avatar">
        </template>
        <template slot="action" slot-scope="record">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              更新
            </a-button>
            <a-popconfirm
              title="确认要永久删除吗?"
              ok-text="确定"
              cancel-text="取消"
              @confirm="handleDelete(record.id)"

            >
              <a-button type="danger">
                删除
              </a-button>
            </a-popconfirm>

          </a-space>
        </template>
      </a-table>
    </a-layout-content>

    <a-modal
        title="分类树"
        v-model:visible="modalVisible"
        :confirm-loading="modalLoading"
        @ok="handleModalOk"
        @cancel="handleCancel"
    >
      <a-form v-model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">

        <a-form-item label="名称">
          <a-input v-model:value="category.name"/>
        </a-form-item>
        <a-form-item label="顺序">
          <a-input v-model:value="category.sort" type="text"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent,onMounted,ref} from "vue";
import axios from "axios";
import { message } from 'ant-design-vue'
import {Tool} from "@/util/tool";
export default defineComponent({
  name:'Admin-Category',
  setup(){
    const param = ref()
    param.value = {}
    const categorys = ref()
    const pagination = ref({
      current:1,
      pageSize:10,
      total:0
    })
    const loading = ref(false)
    const columns = [
      {
        title:'名称',
        dataIndex: 'name'
      },
      {
        title: '父分类',
        key:'pid',
        dataIndex:'pid',
        scopedSlots: {customRender: 'pid'}
      },
      {
        title:'顺序',
        dataIndex: 'sort'
      },
      {
        title:'操作',
        key: 'action',
        scopedSlots: {customRender: 'action'}
      }
    ];
    /**
     * 数据查询
     */
    const handleQuery = (params: any)=>{
      loading.value = true
      axios.get('/category/list',{
        params:{
          page: params.page,
          size: params.size,
          name: param.value.name
        }
      }).then(res =>{
        loading.value = false
        const data = res.data.data
        if (res.data.success){
          categorys.value = data.list
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
    const category = ref({})
    const modalVisible = ref(false)
    const modalLoading = ref(false)
    const handleModalOk = () =>{
      modalLoading.value = true
      axios.post('/category/saveOrUpdate',category.value).then(res =>{
        const data = res.data
        if (data.success){
          modalVisible.value = false
          modalLoading.value = false

          //重新加载列表
          handleQuery({
            page:1,
            size: pagination.value.pageSize
          })
        }
      })
    }


    onMounted(()=>{
      handleQuery({
        page:1,
        size: pagination.value.pageSize
      })
    })


    const handleCancel = ()=>{
      modalVisible.value = false
    }
    //编辑
    const edit = (record:any)=>{
      console.log('记录：'+ record)
      modalVisible.value = true
      category.value = Tool.copy(record)
    }

    //编辑
    const add = ()=>{
      modalVisible.value = true
      category.value = {}
    }
    const handleDelete = (id:number)=>{
      axios.delete('/category/delete/'+id).then(res =>{
        if (res.data.success){
          //重新加载列表
          handleQuery({
            page:pagination.value.current,
            size: pagination.value.pageSize
          })
        }
      })
    }

    return {
      param,
      categorys,
      pagination,
      columns,
      loading,
      handleTableChange,

      edit,
      add,
      handleDelete,
      category,
      modalVisible,
      modalLoading,
      handleModalOk,
      handleCancel,
      handleQuery
    }
  }
})
</script>
<style scoped>

</style>
