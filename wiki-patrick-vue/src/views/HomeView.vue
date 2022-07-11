<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
      >
        <a-menu-item key="welcome">
            <MailOutlined/>
            <span>全部资源</span>
        </a-menu-item>
        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <span> {{item.name}} </span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
            <span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>



    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">

      <div class="welcome" v-show="isShowAll">
        <!--电子书列表-->
        <a-list :loading="loading" item-layout="vertical" size="large" :grid="{ gutter: 20, column: 3 }" :data-source="ebooks">
          <template #renderItem="{ item }">

            <a-card hoverable style="margin-right: 10px;margin-bottom: 10px;">
              <a-list-item key="item.name">
                <template #actions>
                  <span>
                    <component v-bind:is="'EyeOutlined'" style="margin-right: 8px" />
                    {{ item.viewCount }}
                  </span>
                      <span>
                    <component v-bind:is="'HeartOutlined'" style="margin-right: 8px" />
                    {{ item.voteCount }}
                  </span>
                </template>
                <router-link :to="'/detail?id='+item.id">
                <a-list-item-meta :description="item.description">
                  <template #title>
                    <router-link :to="'/detail?id='+item.id">
                      {{item.name}}
                    </router-link>
                  </template>
                  <template #avatar>
                    <a-avatar :src="item.cover" />
                  </template>
                </a-list-item-meta>
                </router-link>
              </a-list-item>
            </a-card>
          </template>
        </a-list>
      </div>

      <!--电子书列表-->
      <a-list :loading="loading" v-show="!isShowAll" item-layout="vertical" size="large" :grid="{ gutter: 20, column: 3 }" :data-source="ebooks">
        <template #renderItem="{ item }">
          <a-card hoverable style="margin-right: 10px;margin-bottom: 10px;">
            <a-list-item key="item.name">
              <template #actions>
                <span v-for="{ type, text } in actions" :key="type">
                  <component v-bind:is="type" style="margin-right: 8px" />
                  {{ text }}
                </span>
              </template>
              <a-list-item-meta :description="item.description">
                <template #title>
                  <a :href="item.href">{{ item.name }}</a>
                </template>
                <template #avatar>
                  <a-avatar :src="item.cover" />
                </template>
              </a-list-item-meta>
            </a-list-item>
          </a-card>
        </template>
      </a-list>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import Vue, { onMounted, ref } from 'vue';
import axios from 'axios'
import { defineComponent } from 'vue';
import {message} from 'ant-design-vue'
import {Tool} from "@/util/tool";


const listData: any = [];

export default defineComponent({
  name: 'HomeView',
  // Vue3新增的初始化方法
  setup() {
    const loading = ref(false)
    const isShowAll = ref(true)
    let categoryId = ''
    const level1 = ref()
    let categorys:any
    const handleQueryCategory = () => {

      axios.get('/category/all').then(res =>{
        const data = res.data
        if (res.data.success){
          categorys = data.data
          console.log('原始数组: ',categorys)
          level1.value = []
          level1.value = Tool.array2Tree(categorys,'000')
          console.log('树形结构',level1.value)
        }else {
          message.error(res.data.message)
        }
      })
    }
    //查询资源数据
    const handleQueryEbook = ()=>{
      loading.value = true
      axios.get('/ebook/list', {
        params:{
          page:1,
          size:10,
          categoryId:categoryId
        }
      }).then((res) => {
        loading.value = false
        const data = res.data.data
        ebooks.value = data.list
      });
    }
    //点击分类菜单
    const handleClick = (value:any)=>{
      console.log('我是 menu click',value)
      if (value.key === 'welcome'){
        categoryId = ''
        isShowAll.value = true
        loading.value = false
        handleQueryEbook()
      }else {
        loading.value = false
        categoryId = value.key
        isShowAll.value = false
        handleQueryEbook()
      }
    }
    console.log('setup')
    const ebooks = ref()



    onMounted(() => {
      handleQueryCategory()
      console.log("onMounted")
      handleQueryEbook()
    })
    return {
      loading,
      handleClick,
      isShowAll,
      level1,
      ebooks,
      listData,
      pagination: {
        onChange: (page: any) => {
          console.log(page);
        },
        pageSize: 3,
      },
      actions: [
        { type: 'star-o', text: '156' },
        { type: 'like-o', text: '156' },
        { type: 'message', text: '2' },
      ],
    }
  }
});
</script>

<style scoped>
.ant-avatar {
  width: 80px;
  height: 80px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
 element.style {
  background: #f5f5f5!important;
}
</style>
