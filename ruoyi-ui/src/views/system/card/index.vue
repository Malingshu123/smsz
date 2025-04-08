<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="卡密" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入卡密"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卡密类型" prop="cardType">
        <el-select v-model="queryParams.cardType" placeholder="请选择卡密类型" clearable>
          <el-option
            v-for="dict in dict.type.card_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="点数" prop="points">
        <el-input
          v-model="queryParams.points"
          placeholder="请输入点数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="creatBy">
        <el-input
          v-model="queryParams.creatBy"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="激活时间" prop="activationDate">
        <el-date-picker clearable
          v-model="queryParams.activationDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择激活时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="到期时间" prop="expirationDate">
        <el-date-picker clearable
          v-model="queryParams.expirationDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择到期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="使用状态" prop="useStatus">
        <el-select v-model="queryParams.useStatus" placeholder="请选择使用状态" clearable>
          <el-option
            v-for="dict in dict.type.card_use_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="登录次数" prop="loginCount">
        <el-input
          v-model="queryParams.loginCount"
          placeholder="请输入登录次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="登录IP" prop="loginIp">
        <el-input
          v-model="queryParams.loginIp"
          placeholder="请输入登录IP"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="登录时间" prop="loginDate">
        <el-date-picker clearable
          v-model="queryParams.loginDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择登录时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="创建时间" prop="createdDate">
        <el-date-picker clearable
          v-model="queryParams.createdDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间" prop="updatedDate">
        <el-date-picker clearable
          v-model="queryParams.updatedDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:card:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:card:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:card:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:card:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cardList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="卡密" align="center" prop="code" />
      <el-table-column label="卡密类型" align="center" prop="cardType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.card_type" :value="scope.row.cardType"/>
        </template>
      </el-table-column>
      <el-table-column label="点数" align="center" prop="points" />
      <el-table-column label="创建人" align="center" prop="creatBy" />
      <el-table-column label="到期时间" align="center" prop="expirationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expirationDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="使用状态" align="center" prop="useStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.card_use_status" :value="scope.row.useStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="登录次数" align="center" prop="loginCount" />
      <el-table-column label="激活时间" align="center" prop="activationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.activationDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="登录IP" align="center" prop="loginIp" />
      <el-table-column label="登录时间" align="center" prop="loginDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loginDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updatedDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatedDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:card:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:card:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加卡密对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="卡密类型" prop="cardType">
          <el-select v-model="form.cardType" placeholder="请选择卡密类型">
            <el-option
              v-for="dict in dict.type.card_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="点数" prop="points">
          <el-input v-model="form.points" placeholder="请输入点数" />
        </el-form-item>
        <el-form-item label="制卡数量" prop="count">
          <el-input v-model="form.count" placeholder="请输入制卡数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改卡密对话框 -->
    <el-dialog :title="statusTitle" :visible.sync="statusOpen" width="500px" append-to-body>
      <el-form ref="statusForm" :model="statusForm" :rules="statusRules" label-width="80px">
        <el-form-item label="卡密类型" prop="cardType">
          <el-select v-model="statusForm.cardType" placeholder="请选择卡密类型">
            <el-option
              v-for="dict in dict.type.card_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="到期时间" prop="expirationDate" v-if="statusForm.cardType !== 'card_type_count'">
          <el-date-picker clearable
                          v-model="statusForm.expirationDate"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择到期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="点数" prop="points" v-if="statusForm.cardType === 'card_type_count'">
          <el-input v-model="statusForm.points" placeholder="请输入点数" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="statusForm.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitStatusForm">确 定</el-button>
        <el-button @click="cancelStatusForm">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCard, getCard, delCard, addCard, updateCard } from "@/api/system/card";

export default {
  name: "Card",
  dicts: ['sys_normal_disable', 'card_type', 'card_use_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 卡密表格数据
      cardList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        cardType: null,
        points: null,
        creatBy: null,
        activationDate: null,
        expirationDate: null,
        useStatus: null,
        status: null,
        loginCount: null,
        loginIp: null,
        loginDate: null,
        createdDate: null,
        updatedDate: null
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
        code: [
          { required: true, message: "卡密不能为空", trigger: "blur" }
        ],
        cardType: [
          { required: true, message: "卡密类型不能为空", trigger: "change" }
        ],
        points: [
          { required: true, message: "点数不能为空", trigger: "blur" }
        ],
        creatBy: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ],
      },
      statusOpen: false,
      statusTitle: "",
      statusForm: {},
      statusRules: {
        code: [
          { required: true, message: "卡密不能为空", trigger: "blur" }
        ],
        cardType: [
          { required: true, message: "卡密类型不能为空", trigger: "change" }
        ],
        points: [
          { required: true, message: "点数不能为空", trigger: "blur" }
        ],
        creatBy: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询卡密列表 */
    getList() {
      this.loading = true;
      listCard(this.queryParams).then(response => {
        this.cardList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 取消状态修改 */
    cancelStatusForm() {
      this.statusOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        code: null,
        cardType: null,
        points: null,
        creatBy: null,
        activationDate: null,
        expirationDate: null,
        useStatus: null,
        status: null,
        loginCount: null,
        loginIp: null,
        loginDate: null,
        createdDate: null,
        updatedDate: null
      };
      this.resetForm("form");
    },
    // 表单重置
    addReset() {
      this.form = {
        id: null,
        code: null,
        cardType: "card_type_count",
        points: 100,
        count: 1,
        creatBy: null,
        activationDate: null,
        expirationDate: null,
        useStatus: null,
        status: null,
        loginCount: null,
        loginIp: null,
        loginDate: null,
        createdDate: null,
        updatedDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.addReset();
      this.open = true;
      this.title = "添加卡密";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCard(id).then(response => {
        this.statusForm = response.data;
        this.statusOpen = true;
        this.statusTitle = "修改卡密";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCard(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.statusOpen = false;
              this.getList();
            });
          } else {
            addCard(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交状态修改 */
    submitStatusForm() {
      this.$refs["statusForm"].validate(valid => {
        if (valid) {
          updateCard(this.statusForm).then(response => {
            this.$modal.msgSuccess("状态修改成功");
            this.statusOpen = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除卡密编号为"' + ids + '"的数据项？').then(function() {
        return delCard(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/card/export', {
        ...this.queryParams
      }, `card_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
