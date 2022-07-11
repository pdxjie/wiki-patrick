<template>
  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px',backgroundColor:'#EFF2F5' }">
      <a-row>
        <a-col :span="18">
          <a-card title="资源详情" style="width: 98%">
            <a-row>
              <a-col :span="6"></a-col>
              <a-col :span="12">
                <div class="img-center">
                  <img :src="ebook.cover" :alt="ebook.name">
                </div>
                <div class="resource-name">
                  <h4>{{ebook.name}}</h4>
                </div>
                <div class="resource-data">
                  <div :style="{ whiteSpace: 'nowrap' }">
                    <a-tooltip placement="topLeft">
                      <template #title>
                        <span>浏览量</span>
                      </template>
                      <a-button size="small" type="text" style="background-color: #b0afa5;margin-right: 10px"> <EyeOutlined />
                        {{ ebook.viewCount }} </a-button>
                    </a-tooltip>
                    <a-tooltip placement="topLeft">
                      <template #title>
                        <span>收藏</span>
                      </template>
                      <a-button size="small" type="text" style="background-color: #ee1616;margin-right: 10px"> <HeartOutlined />
                        {{ ebook.voteCount }} </a-button>
                    </a-tooltip>
                  </div>
                  <div class="resource-description">
                    <p>{{ ebook.description }}</p>
                  </div>
                </div>
              </a-col>
              <a-col :span="6"></a-col>
            </a-row>
            <a-row>
              <a-divider style="border-color: #cdcaca;margin-bottom: 30px" dashed />
              <div>
                链接🔗：<a :href="'http://www.baidu.com'">http://www.baidu.com</a>
              </div>
            </a-row>
            <a-row>
              <div style="margin-top: 10px">
                详情🔖：这是一款极其好用的Vue框架，使用于前后端分离
              </div>
            </a-row>
            <a-row>
              <div style="margin-top: 10px">
                时间⏰：2022-7-11
              </div>
            </a-row>
            <a-row>
              <div style="margin-top: 10px">
                荐者🕵：派大星
              </div>
            </a-row>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card title="相似资源" style="width: 99%">
            <a-list :loading="loading" item-layout="vertical" size="large" :data-source="ebooks">
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
                        <a :href="item.href">{{ item.name }}</a>
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
          </a-card>
        </a-col>
      </a-row>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue'
import {useRoute} from "vue-router";
import { SmileTwoTone, HeartTwoTone,EyeOutlined,HeartOutlined } from '@ant-design/icons-vue';
import axios from "_axios@0.27.2@axios";
export default defineComponent({
  name:'Detail',
  components:{
    EyeOutlined,HeartOutlined
  },
  setup(){
    const route = useRoute()
    console.log('这是什么：',route.query.id)
    const ebooks = ref()
    const loading = ref(false)
    const ebook = ref({
      cover:'',
      name:'',
      description:'',
      viewCount: 0,
      voteCount: 0
    })


    const handleQueryEbookById = ()=>{
      axios.get('/ebook/find-ebook/'+route.query.id).then(res =>{
        console.log('ebook====>',res)
        ebook.value.cover = res.data.data.cover
        ebook.value.name = res.data.data.name
        ebook.value.viewCount = res.data.data.viewCount
        ebook.value.voteCount = res.data.data.voteCount
        ebook.value.description = res.data.data.description
      })
    }


    //查询资源数据
    const handleQueryEbook = ()=>{
      loading.value = true
      axios.get('/ebook/list', {
        params:{
          page:1,
          size:10
        }
      }).then((res) => {
        loading.value = false
        const data = res.data.data
        ebooks.value = data.list
      });
    }
    onMounted(() => {
      handleQueryEbook()
      handleQueryEbookById()
    })



    return {
      ebooks,
      loading,
      ebook,
      // actions: [
      //   { type: 'star-o', text: '156' },
      //   { type: 'like-o', text: '156' },
      // ],
    }



  }

})
</script>

<style scoped>
body{
  background: #ececec;
}
img {

}
.img-center {
  text-align: center;
}
.img-center img {
  width: 100px;
  height: 100px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
.resource-data {
  text-align: center;
}
 .icons-list :deep(.anticon) {
   margin-right: 6px;
   font-size: 15px;
 }
 .resource-description{
   text-align: center;
   margin-top: 10px;
 }
 .resource-name{
   text-align: center;
   margin-bottom: 10px;
   font-size: 25px;
 }
.ant-avatar {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
</style>