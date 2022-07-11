<template>
  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
      <a-row>
        <a-col :span="18">
          <a-card title="资源详情" style="width: 100%">
            <div class="img-position">
              <img src="../assets/logo.png" alt="">
            </div>
            <div>
              <a style="text-align: center;font-weight: 500;font-size: 24px;letter-spacing: 1px;">这是一个资源</a>
            </div>
          </a-card>
        </a-col>
        <a-col :span="6">
          这是推荐
        </a-col>
      </a-row>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {useRoute} from "vue-router";
export default defineComponent({
  name:'Detail',
  setup(){
    const route = useRoute()
    console.log('这是什么：',route.query.id)
  }

})
</script>

<style scoped>
body{
  background: #ececec;
}
img{
  width: 100px;
  height: 100px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
</style>