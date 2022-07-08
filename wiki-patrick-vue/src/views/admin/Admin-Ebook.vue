<template>

  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="ebooks"
        :pagination="pagination"
        :loading="loading"
        @change="handleTableChange"
      >
        <template v-slot:cover="{text:cover}">
          <img v-if="cover" :src="cover" alt="avatar">
        </template>
        <template v-slot:action="{text,record}">
          <a-space size="small">
            <a-button type="primary" @click="edit">
              更新
            </a-button>
            <a-button type="danger">
              删除
            </a-button>
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
<!--      <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">-->
<!--        <a-form-item label="封面">-->
<!--          <a-input v-model:value="ebook.cover" />-->
<!--        </a-form-item>-->
<!--        <a-form-item label="名称">-->
<!--          <a-input v-model:value="ebook.name"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="分类一">-->
<!--          <a-input v-model:value="ebook.categoryId"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="分类二">-->
<!--          <a-input v-model:value="ebook.categoryPid"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="描述">-->
<!--          <a-input v-model:value="ebook.description" type="text"/>-->
<!--        </a-form-item>-->
<!--      </a-form>-->
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
      pageSize:1,
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
    onMounted(()=>{
      handleQuery({
        page:1,
        size: pagination.value.pageSize
      })
    })
    //表单
    const modalVisible = ref(false)
    const modalLoading = ref(false)
    const handleModalOk = () =>{
      modalLoading.value = true
      setTimeout(()=>{
        modalVisible.value = false
        modalLoading.value = false
      },2000)
    }

    const handleCancel = ()=>{
      modalVisible.value = false
    }
    //编辑
    const edit = ()=>{
      modalVisible.value = true
    }






    return {
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,

      edit,
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
