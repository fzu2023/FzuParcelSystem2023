<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">网点id</label>
        <el-input v-model="query.outletId" clearable placeholder="网点id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="网点id">
            <el-input v-model="form.outletId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="当前容量">
            <el-input v-model="form.currentCapacity" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="总容量">
            <el-input v-model="form.totalCapacity" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="网点名字">
            <el-input v-model="form.outName" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="outletId" label="网点id" />
        <el-table-column prop="currentCapacity" label="当前容量" />
        <el-table-column prop="totalCapacity" label="总容量" />
        <el-table-column prop="outName" label="网点名字" />
        <el-table-column v-if="checkPer(['admin','fzuWarehouseInfo:edit','fzuWarehouseInfo:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudFzuWarehouseInfo from '@/api/fzuWarehouseInfo'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, outletId: null, currentCapacity: null, totalCapacity: null, outName: null }
export default {
  name: 'FzuWarehouseInfo',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '仓库管理', url: 'api/fzuWarehouseInfo', idField: 'id', sort: 'id,desc', crudMethod: { ...crudFzuWarehouseInfo }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'fzuWarehouseInfo:add'],
        edit: ['admin', 'fzuWarehouseInfo:edit'],
        del: ['admin', 'fzuWarehouseInfo:del']
      },
      rules: {
      },
      queryTypeOptions: [
        { key: 'outletId', display_name: '网点id' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
