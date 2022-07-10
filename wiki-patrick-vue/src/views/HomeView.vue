<template>
  <a-layout>
    <!-- <a-layout-sider width="200" style="background: #fff">
      <a-menu mode="inline" :default-selected-keys="['1']" :default-open-keys="['sub1']"
        :style="{ height: '100%', borderRight: 0 }">
        <a-sub-menu key="sub1">
          <span slot="title">
            <a-icon type="user" />subnav 1111
          </span>
          <a-menu-item key="1">
            option1
          </a-menu-item>
          <a-menu-item key="2">
            option2
          </a-menu-item>
          <a-menu-item key="3">
            option3
          </a-menu-item>
          <a-menu-item key="4">
            option4
          </a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub2">
          <span slot="title">
            <a-icon type="laptop" />subnav 2
          </span>
          <a-menu-item key="5">
            option5
          </a-menu-item>
          <a-menu-item key="6">
            option6
          </a-menu-item>
          <a-menu-item key="7">
            option7
          </a-menu-item>
          <a-menu-item key="8">
            option8
          </a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub3">
          <span slot="title">
            <a-icon type="notification" />subnav 3
          </span>
          <a-menu-item key="9">
            option9
          </a-menu-item>
          <a-menu-item key="10">
            option10
          </a-menu-item>
          <a-menu-item key="11">
            option11
          </a-menu-item>
          <a-menu-item key="12">
            option12
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider> -->
    <a-layout-sider width="200" style="background: #fff">
      <a-menu mode="inline" v-model:selectedKeys="selectedKeys2" v-model:openKeys="openKeys"
        :style="{ height: '100%', borderRight: 0 }">
        <a-sub-menu key="sub1">
          <template #title>
            <span>
              <user-outlined />
              subnav 1
            </span>
          </template>
          <a-menu-item key="1">option1</a-menu-item>
          <a-menu-item key="2">option2</a-menu-item>
          <a-menu-item key="3">option3</a-menu-item>
          <a-menu-item key="4">option4</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub2">
          <template #title>
            <span>
              <laptop-outlined />
              subnav 2
            </span>
          </template>
          <a-menu-item key="5">option5</a-menu-item>
          <a-menu-item key="6">option6</a-menu-item>
          <a-menu-item key="7">option7</a-menu-item>
          <a-menu-item key="8">option8</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub3">
          <template #title>
            <span>
              <notification-outlined />
              subnav 3
            </span>
          </template>
          <a-menu-item key="9">option9</a-menu-item>
          <a-menu-item key="10">option10</a-menu-item>
          <a-menu-item key="11">option11</a-menu-item>
          <a-menu-item key="12">option12</a-menu-item>
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
    console.log('setup')
    const ebooks = ref()
    const queryVo = {
      id: null,
      name: null
    }
    onMounted(() => {
      console.log("onMounted")
      axios.post('/ebook/list', queryVo).then((res) => {
        const data = res.data.data
        ebooks.value = data
      });
    })
    return {
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
