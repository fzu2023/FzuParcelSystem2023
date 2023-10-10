<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">网点名字</label>
        <el-input v-model="query.nickname" clearable placeholder="网点名字" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">负责人电话</label>
        <el-input v-model="query.adminPhone" clearable placeholder="负责人电话" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="网点名字" prop="nickname">
            <el-input v-model="form.nickname" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="负责人电话" prop="adminPhone">
            <el-input v-model="form.adminPhone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="负责人邮箱">
            <el-input v-model="form.adminEmail" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="现有容量/仓房间数">
            <el-input v-model="form.currentCapacity" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="总容量/仓库房间数">
            <el-input v-model="form.totalCapacity" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="网点id">
            <el-input v-model="form.nickid" style="width: 370px;" />
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
        <el-table-column prop="nickname" label="网点名字" />
        <el-table-column prop="adminPhone" label="负责人电话" />
        <el-table-column prop="adminEmail" label="负责人邮箱" />
        <el-table-column prop="currentCapacity" label="现有容量/仓房间数" />
        <el-table-column prop="totalCapacity" label="总容量/仓库房间数" />
        <el-table-column prop="nickid" label="网点id" />
        <el-table-column v-if="checkPer(['admin','fzuOutletInfo:edit','fzuOutletInfo:del'])" label="操作" width="150px" align="center">
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
import crudFzuOutletInfo from '@/api/fzuOutletInfo'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, nickname: null, adminPhone: null, adminEmail: null, currentCapacity: null, totalCapacity: null, nickid: null }
export default {
  name: 'FzuOutletInfo',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '网点信息表', url: 'api/fzuOutletInfo', idField: 'id', sort: 'id,desc', crudMethod: { ...crudFzuOutletInfo }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'fzuOutletInfo:add'],
        edit: ['admin', 'fzuOutletInfo:edit'],
        del: ['admin', 'fzuOutletInfo:del']
      },
      rules: {
        nickname: [
          { required: true, message: '网点名字不能为空', trigger: 'blur' }
        ],
        adminPhone: [
          { required: true, message: '负责人电话不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'nickname', display_name: '网点名字' },
        { key: 'adminPhone', display_name: '负责人电话' }
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
