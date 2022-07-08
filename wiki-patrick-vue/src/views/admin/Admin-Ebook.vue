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
        @change="handleTabaleChange"
      >
        <template #cover="{text:cover}">
          <img v-if="cover" :src="cover" alt="avatar">
        </template>
        <template v-slot:action="{text,record}">
          <a-space size="small">
            <a-button type="primary">
              更新
            </a-button>
            <a-button type="danger">
              删除
            </a-button>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
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
      pageSize:2,
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
          ebooks.value = data

          //重置分页按钮
          pagination.value.current = params.page
        })
      }

    /**
     * 表格点击页码时出发
     */
    const handleTabaleChange = (pagination: any)=>{
      console.log('看看自带的分页参数'+pagination)
      handleQuery({
        page:pagination.current,
        size:pagination.pageSize
      })
    }
    onMounted(()=>{
      handleQuery({})
    })
    return {
      ebooks,
      pagination,
      columns,
      loading,
      handleTabaleChange
    }
  }
})
</script>
<style scoped>

</style>
