<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">所属部门</label>
        <el-input v-model="query.dept" clearable placeholder="所属部门" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="发布人" prop="author">
            <el-input v-model="form.author" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属部门">
            <el-select v-model="form.dept" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.outlet_status"
                :key="item.id"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="form.content" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新时间">
            <el-date-picker v-model="form.updateTime" type="datetime" style="width: 370px;" />
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
        <el-table-column prop="id" label="id" />
        <el-table-column prop="author" label="发布人" />
        <el-table-column prop="dept" label="所属部门">
          <template slot-scope="scope">
            {{ dict.label.outlet_status[scope.row.dept] }}
          </template>
        </el-table-column>
        <el-table-column prop="content" label="内容" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column v-if="checkPer(['admin','fzuNoticesInfo:edit','fzuNoticesInfo:del'])" label="操作" width="150px" align="center">
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
import crudFzuNoticesInfo from '@/api/fzuNoticesInfo'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, author: null, dept: null, content: null, updateTime: null }
export default {
  name: 'FzuNoticesInfo',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['outlet_status'],
  cruds() {
    return CRUD({ title: '通知列表', url: 'api/fzuNoticesInfo', idField: 'id', sort: 'id,desc', crudMethod: { ...crudFzuNoticesInfo }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'fzuNoticesInfo:add'],
        edit: ['admin', 'fzuNoticesInfo:edit'],
        del: ['admin', 'fzuNoticesInfo:del']
      },
      rules: {
        author: [
          { required: true, message: '发布人不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'dept', display_name: '所属部门' }
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
