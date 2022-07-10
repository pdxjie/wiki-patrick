<template>
    <a-layout>
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
            <p>
                <a-form layout="inline" v-model="param">
                    <a-form-item>
                        <a-button type="primary" @click="handleQuery()">
                            刷新数据
                        </a-button>
                    </a-form-item>
                    <a-form-item>
                        <a-button type="primary" @click="add()">
                            新增
                        </a-button>
                    </a-form-item>
                </a-form>
            </p>
            <a-table :columns="columns" :row-key="record => record.id" :data-source="level1" :pagination="false"
                :loading="loading" @change="handleTableChange">
                <template #cover="{ text: cover }">
                    <img v-if="cover" :src="cover" alt="avatar">
                </template>
                <template #action="{record}">
                    <a-space size="small">
                        <a-button type="primary" @click="edit(record)">
                            更新
                        </a-button>
                        <a-popconfirm title="确认要永久删除吗?" ok-text="确定" cancel-text="取消"
                            @confirm="handleDelete(record.id)">
                            <a-button type="danger">
                                删除
                            </a-button>
                        </a-popconfirm>

                    </a-space>
                </template>
            </a-table>
        </a-layout-content>

        <a-modal title="分类树" v-model:visible="modalVisible" :confirm-loading="modalLoading" @ok="handleModalOk"
            @cancel="handleCancel">
            <a-form v-model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">

                <a-form-item label="名称">
                    <a-input v-model:value="category.name" />
                </a-form-item>
                <a-form-item label="父级分类">
                    <a-select v-model:value="category.pid" ref="select">
                        <a-select-option value="000">
                            无
                        </a-select-option>
                        <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="category.id === c.id">
                            {{ c.name }}
                        </a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="顺序">
                    <a-input v-model:value="category.sort" type="text" />
                </a-form-item>
            </a-form>
        </a-modal>
    </a-layout>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import axios from "axios";
import { message } from 'ant-design-vue'
import { Tool } from "@/util/tool";
export default defineComponent({
    name: 'Admin-Category',
    setup() {
        const param = ref()
        param.value = {}
        const categorys = ref()
        const loading = ref(false)
        const columns = [
            {
                title: '名称',
                dataIndex: 'name'
            },
            {
                title: '父分类',
                key: 'pid',
                dataIndex: 'pid',
                scopedSlots: { customRender: 'pid' }
            },
            {
                title: '顺序',
                dataIndex: 'sort'
            },
            {
                title: '操作',
                dataIndex: 'action',
                slots: { customRender: 'action' }
            }
        ];

        const level1 = ref()
        level1.value = []
        /**
         * 数据查询
         */
        const handleQuery = () => {
            loading.value = true
            level1.value = []
            axios.get('/category/all').then(res => {
                loading.value = false
                const data = res.data.data
                if (res.data.success) {
                    categorys.value = data
                    console.log('原始数组', categorys.value)
                    level1.value = []
                    level1.value = Tool.array2Tree(categorys.value, '000')
                    console.log('树形结构', level1.value)
                } else {
                    message.error(res.data.message)
                }
            })
        }


        //表单
        const category = ref({})
        const modalVisible = ref(false)
        const modalLoading = ref(false)
        const handleModalOk = () => {
            modalLoading.value = true
            axios.post('/category/saveOrUpdate', category.value).then(res => {
                const data = res.data
                if (data.success) {
                    modalVisible.value = false
                    modalLoading.value = false
                    message.success('操作成功')
                    //重新加载列表
                    handleQuery()
                }
            })
        }


        onMounted(() => {
            handleQuery()
        })


        const handleCancel = () => {
            modalVisible.value = false
        }
        //编辑
        const edit = (record: any) => {
            console.log('记录：' + record)
            modalVisible.value = true
            category.value = Tool.copy(record)
        }

        //编辑
        const add = () => {
            modalVisible.value = true
            category.value = {}
        }
        const handleDelete = (id: number) => {
            axios.delete('/category/delete/' + id).then(res => {
                if (res.data.success) {
                    message.success('删除成功')
                    //重新加载列表
                    handleQuery()
                }
            })
        }

        return {
            param,
            //categorys,
            level1,
            columns,
            loading,
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
