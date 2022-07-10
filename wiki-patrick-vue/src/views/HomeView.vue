<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
      >

        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <span> <user-outlined/>{{item.name}} </span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
            <MailOutlined/><span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>

    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
      <!--电子书列表-->

      <a-list item-layout="vertical" size="large" :data-source="ebooks">
        <template #renderItem="{ item }">
          <a-list-item key="item.title">
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
for (let i = 0; i < 23; i++) {
  listData.push({
    href: 'https://www.antdv.com/',
    title: `ant design vue part ${i}`,
    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
    description:
      'Ant Design, a design language for background applications, is refined by Ant UED Team.',
    content:
      'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
  });
}

export default defineComponent({
  name: 'HomeView',
  // Vue3新增的初始化方法
  setup() {
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
    const handleClick = ()=>{
      console.log('menu click')
    }










    console.log('setup')
    const ebooks = ref()
    const queryVo = {
      id: null,
      name: null
    }
    onMounted(() => {
      handleQueryCategory()
      console.log("onMounted")
      axios.post('/ebook/list', queryVo).then((res) => {
        const data = res.data.data
        ebooks.value = data
      });
    })
    return {
      handleClick,
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
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
</style>
