<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">用户账户</label>
        <el-input v-model="query.username" clearable placeholder="用户账户" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="用户地址">
            <el-input v-model="form.userAddress" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="用户电话">
            <el-input v-model="form.userPhone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="用户账户" prop="username">
            <el-input v-model="form.username" style="width: 370px;" />
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
        <el-table-column prop="userAddress" label="用户地址" />
        <el-table-column prop="userPhone" label="用户电话" />
        <el-table-column prop="username" label="用户账户" />
        <el-table-column v-if="checkPer(['admin','fzuUserAddress:edit','fzuUserAddress:del'])" label="操作" width="150px" align="center">
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
import crudFzuUserAddress from '@/api/fzuUserAddress'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, userId: null, userAddress: null, userPhone: null, username: null }
export default {
  name: 'FzuUserAddress',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '用户地址', url: 'api/fzuUserAddress', idField: 'id', sort: 'id,desc', crudMethod: { ...crudFzuUserAddress }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'fzuUserAddress:add'],
        edit: ['admin', 'fzuUserAddress:edit'],
        del: ['admin', 'fzuUserAddress:del']
      },
      rules: {
        username: [
          { required: true, message: '用户账户不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'username', display_name: '用户账户' }
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
