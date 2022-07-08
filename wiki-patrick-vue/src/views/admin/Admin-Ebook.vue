<template>

  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-button type="primary" @click="add()">
          推荐
        </a-button>
      </p>
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="ebooks"
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
        title="资源表单"
        v-model:visible="modalVisible"
        :confirm-loading="modalLoading"
        @ok="handleModalOk"
        @cancel="handleCancel"
    >
      <a-form v-model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="封面">
          <a-input v-model:value="ebook.cover" />
        </a-form-item>
        <a-form-item label="名称">
          <a-input v-model:value="ebook.name"/>
        </a-form-item>
        <a-form-item label="分类一">
          <a-input v-model:value="ebook.categoryId"/>
        </a-form-item>
        <a-form-item label="分类二">
          <a-input v-model:value="ebook.categoryPid"/>
        </a-form-item>
        <a-form-item label="描述">
          <a-input v-model:value="ebook.description" type="text"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent,onMounted,ref} from "vue";
import axios from "axios";
export default defineComponent({
  name:'Admin-Ebook',
  setup(){
    const ebooks = ref()
    const pagination = ref({
      current:1,
      pageSize:10,
      total:0
    })
    const loading = ref(false)
    const columns = [
      {
        title:'封面',
        dataIndex:'cover',
      },
      {
        title:'名称',
        dataIndex: 'name'
      },
      {
        title: '一级分类',
        key:'categoryId',
        dataIndex:'categoryId',
        scopedSlots: {customRender: 'name'}
      },
      {
        title: '二级分类',
        dataIndex:'categoryPid'
      },
      {
        title:'文档数',
        dataIndex: 'docCount'
      },
      {
        title:'阅读数',
        dataIndex: 'viewCount'
      },
      {
        title:'点赞数',
        dataIndex: 'voteCount'
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
      axios.post('/ebook/list',params).then(res =>{
        loading.value = false
        const data = res.data.data
        ebooks.value = data.list
        //重置分页按钮
        pagination.value.current = params.page
        pagination.value.total = res.data.data.total
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
    const ebook = ref({})
    const modalVisible = ref(false)
    const modalLoading = ref(false)
    const handleModalOk = () =>{
      modalLoading.value = true
      axios.post('/ebook/saveOrUpdate',ebook.value).then(res =>{
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
      ebook.value = record
    }

    //编辑
    const add = ()=>{
      modalVisible.value = true
      ebook.value = {}
    }
    const handleDelete = (id:number)=>{
      axios.delete('/ebook/delete/'+id).then(res =>{
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
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,

      edit,
      add,
      handleDelete,
      ebook,
      modalVisible,
      modalLoading,
      handleModalOk,
      handleCancel
    }
  }
})
</script>
<style scoped>

</style>
