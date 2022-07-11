<template>
    <a-layout-header class="header">
        <div class="logo" />
        <a-menu theme="dark" mode="horizontal" :default-selected-keys="['2']" :style="{ lineHeight: '64px' }">
            <a-menu-item key="/">
                <router-link to="/">首页</router-link>
            </a-menu-item>

            <a-menu-item key="/admin/ebook" v-show="true">
              <router-link to="/admin/ebook">资源管理</router-link>
            </a-menu-item>

            <a-menu-item key="/admin/category" v-show="isShowAll">
                <router-link to="/admin/category">分类管理</router-link>
            </a-menu-item>
            <a-menu-item key="/about">
              <router-link to="/about">关于我们</router-link>
            </a-menu-item>
        </a-menu>
    </a-layout-header>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
export default defineComponent({
    name:'TheHeader',
  setup(){
    const isShowAll = ref(false)
    return {
      isShowAll
    }
  }
})
</script>

<style scoped>
</style>