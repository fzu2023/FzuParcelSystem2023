<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">寄件人电话</label>
        <el-input v-model="query.senderPhone" clearable placeholder="寄件人电话" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">订单状态</label>
        <el-input v-model="query.itemStatus" clearable placeholder="订单状态" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">订单id</label>
        <el-input v-model="query.itemId" clearable placeholder="订单id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">寄件网点</label>
        <el-input v-model="query.senderOutlet" clearable placeholder="寄件网点" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="寄件人">
            <el-input v-model="form.sender" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="收件人">
            <el-input v-model="form.recipient" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="寄出地址">
            <el-input v-model="form.senderAddress" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="发往地址">
            <el-input v-model="form.recipientAddress" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="寄件人电话" prop="senderPhone">
            <el-input v-model="form.senderPhone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="收件人电话">
            <el-input v-model="form.recipientPhone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="订单状态">
            <el-select v-model="form.itemStatus" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.item_status"
                :key="item.id"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="订单id" prop="itemId">
            <el-input v-model="form.itemId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="订单生成时间">
            <el-date-picker v-model="form.itemStartTime" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="订单完成时间">
            <el-date-picker v-model="form.itemEndTime" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="寄件网点">
            <el-select v-model="form.senderOutlet" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.outlet_status"
                :key="item.id"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="收件网点">
            <el-select v-model="form.recipientOutlet" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.outlet_status"
                :key="item.id"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="派件员用户">
            <el-input v-model="form.deliveryUsername" style="width: 370px;" />
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
        <el-table-column prop="sender" label="寄件人" />
        <el-table-column prop="recipient" label="收件人" />
        <el-table-column prop="senderAddress" label="寄出地址" />
        <el-table-column prop="recipientAddress" label="发往地址" />
        <el-table-column prop="senderPhone" label="寄件人电话" />
        <el-table-column prop="recipientPhone" label="收件人电话" />
        <el-table-column prop="itemStatus" label="订单状态">
          <template slot-scope="scope">
            {{ dict.label.item_status[scope.row.itemStatus] }}
          </template>
        </el-table-column>
        <el-table-column prop="itemId" label="订单id" />
        <el-table-column prop="itemStartTime" label="订单生成时间" />
        <el-table-column prop="itemEndTime" label="订单完成时间" />
        <el-table-column prop="deliveryUsername" label="派件员用户" />
        <el-table-column v-if="checkPer(['admin','fzuItemInfo:edit','fzuItemInfo:del'])" label="操作" width="150px" align="center">
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
import crudFzuItemInfo from '@/api/fzuItemInfo'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, sender: null, recipient: null, senderAddress: null, recipientAddress: null, senderPhone: null, recipientPhone: null, itemStatus: null, itemId: null, itemStartTime: null, itemEndTime: null, senderOutlet: null, recipientOutlet: null, deliveryUsername: null }
export default {
  name: 'FzuItemInfo',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['item_status', 'outlet_status'],
  cruds() {
    return CRUD({ title: '订单列表', url: 'api/fzuItemInfo', idField: 'id', sort: 'id,desc', crudMethod: { ...crudFzuItemInfo }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'fzuItemInfo:add'],
        edit: ['admin', 'fzuItemInfo:edit'],
        del: ['admin', 'fzuItemInfo:del']
      },
      rules: {
        senderPhone: [
          { required: true, message: '寄件人电话不能为空', trigger: 'blur' }
        ],
        itemId: [
          { required: true, message: '订单id不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'senderPhone', display_name: '寄件人电话' },
        { key: 'itemStatus', display_name: '订单状态' },
        { key: 'itemId', display_name: '订单id' },
        { key: 'senderOutlet', display_name: '寄件网点' }
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
