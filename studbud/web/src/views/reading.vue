<template>
  <div class="reading">
    <div class="left">
      <el-tree :data="data" :props="defaultProps" icon-class="el-icon-folder" default-expand-all @node-click="handleNodeClick">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span>{{ node.label }}</span>
          <el-dropdown v-if="!data.parentId">
            <i class="el-icon-more" style="transform: rotate(90deg);cursor: pointer;"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="bookmarkVisible = true;bookmark.parentId = data.id">Add Bookmark</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </span>
      </el-tree>
      <div style="text-align: center">
        <i @click="folderVisible = true" class="el-icon-circle-plus"></i>
      </div>
    </div>
    <div class="right">
      <div class="head">
        <i class="el-icon-refresh" @click="refresh"></i>
        <input v-model="iframeSrc" class="input-a" type="text">
      </div>
      <div class="chart">
        <iframe class="calendar" :src="iframeSrc" frameborder="0"></iframe>
      </div>
    </div>

    <el-dialog
      width="461px"
      title="Add New Folder"
      :visible.sync="folderVisible">
      <el-input v-model="folderName" placeholder="Folder Name*"></el-input>
      <el-button @click="createFolder" style="width: 100%;background: rgb(98, 84, 234);color: #fff;margin: 16px 0 0;">Create Folder</el-button>
    </el-dialog>
    <el-dialog
      width="461px"
      title="Add New Bookmark"
      :visible.sync="bookmarkVisible">
      <el-input v-model="bookmark.address" placeholder="Bookmark Address*"></el-input>
      <el-button @click="createBookmark" style="width: 100%;background: rgb(98, 84, 234);color: #fff;margin: 16px 0 0;">Create Bookmark</el-button>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      data: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      folderVisible: false,
      folderName: '',
      bookmarkVisible: false,
      bookmark: {
        address: '',
        parentId: 0
      },
      iframeSrc: ''
    }
  },
  created () {
    this.fetchList()
  },
  methods: {
    handleNodeClick (data) {
      if (data.parentId) {
        this.iframeSrc = data.label
      }
    },
    async fetchList () {
      const { data } = await axios.get(`${this.$host}/bookmark`)
      this.data = data.filter(item => !item.parentId).map(item => {
        return {
          id: item.id,
          label: item.address,
          parentId: null,
          children: data.filter(bb => bb.parentId === item.id).map(bb => {
            return {
              id: bb.id,
              label: bb.address,
              parentId: bb.parentId
            }
          })
        }
      })
      console.log(this.data)
    },
    async createFolder () {
      await axios.post(`${this.$host}/bookmark`, {
        address: this.folderName,
        parentId: 0
      })
      this.folderName = ''
      this.folderVisible = false
      this.fetchList()
    },
    async createBookmark () {
      await axios.post(`${this.$host}/bookmark`, this.bookmark)
      this.bookmark = {
        address: '',
        parentId: 0
      }
      this.bookmarkVisible = false
      this.fetchList()
    },
    refresh () {
      if (!this.iframeSrc) {
        return
      }
      const t = this.iframeSrc
      this.iframeSrc = ''
      this.$nextTick(() => {
        this.iframeSrc = t
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.reading {
  display: flex;
  height: 100%;
  .left {
    padding: 16px 8px;
    width: 250px;
    height: 100%;
    border-right: 1px solid rgb(153, 155, 242);
  }
  .right {
    flex:1;
    display: flex;
    flex-direction: column;
    .head {
      display: flex;
      align-items: center;
      width: 100%;
      padding: 8px;
      border-bottom: 1px solid rgb(153, 155, 242);
      .input-a {
        flex: 1;
        padding: 4px 8px;
        background: rgb(153, 155, 242);
        border-radius: 100px;
        border: none;
        margin: 0 0 0 8px;
      }
    }
    .chart {
      flex: 1;
      width: 100%;
      .calendar {
        width: 100%;
        height: 100%;
      }
    }
  }
  .el-icon-circle-plus {
    color: rgb(153, 155, 242);
    font-size: 80px;
    cursor: pointer;
  }
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
}
</style>
