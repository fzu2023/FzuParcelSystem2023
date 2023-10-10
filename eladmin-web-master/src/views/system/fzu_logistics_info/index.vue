<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">订单id</label>
        <el-input v-model="query.itemId" clearable placeholder="订单id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">司机名字</label>
        <el-input v-model="query.driverName" clearable placeholder="司机名字" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="订单id">
            <el-input v-model="form.itemId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="发出城市">
            <el-input v-model="form.senderCity" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="当前所在城市">
            <el-input v-model="form.currentCity" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="接收城市">
            <el-input v-model="form.recipientCity" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="司机名字">
            <el-input v-model="form.driverName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="发车开始时间">
            <el-date-picker v-model="form.startDate" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="卸货结束时间">
            <el-date-picker v-model="form.endDate" type="datetime" style="width: 370px;" />
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
        <el-table-column prop="itemId" label="订单id" />
        <el-table-column prop="senderCity" label="发出城市" />
        <el-table-column prop="currentCity" label="当前所在城市" />
        <el-table-column prop="recipientCity" label="接收城市" />
        <el-table-column prop="driverName" label="司机名字" />
        <el-table-column prop="startDate" label="发车开始时间" />
        <el-table-column prop="endDate" label="卸货结束时间" />
        <el-table-column v-if="checkPer(['admin','fzuLogisticsInfo:edit','fzuLogisticsInfo:del'])" label="操作" width="150px" align="center">
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
import crudFzuLogisticsInfo from '@/api/fzuLogisticsInfo'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, itemId: null, senderCity: null, currentCity: null, recipientCity: null, driverName: null, startDate: null, endDate: null }
export default {
  name: 'FzuLogisticsInfo',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '物流管理', url: 'api/fzuLogisticsInfo', idField: 'id', sort: 'id,desc', crudMethod: { ...crudFzuLogisticsInfo }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'fzuLogisticsInfo:add'],
        edit: ['admin', 'fzuLogisticsInfo:edit'],
        del: ['admin', 'fzuLogisticsInfo:del']
      },
      rules: {
      },
      queryTypeOptions: [
        { key: 'itemId', display_name: '订单id' },
        { key: 'driverName', display_name: '司机名字' }
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
