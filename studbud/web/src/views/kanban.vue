<template>
  <div class="kanban">
    <div v-for="(category, index) in categoryList" :key="index" class="category">
      <div class="head">
        <div class="name" :style="{ background: category.color }">{{ category.name }}</div>
        <el-dropdown :hide-on-click="false" trigger="click">
          <i class="el-icon-more" style="transform: rotate(90deg);cursor: pointer;"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="taskVisible = true;taskTemp.categoryId = category.id">Add</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

      <div class="switch">
        <el-switch v-model="categoryList[index].unfinished" active-color="#4e42c7"></el-switch>
        <div class="text">Unfinished</div>
        <el-switch v-model="categoryList[index].finished" active-color="#4e42c7"></el-switch>
        <div class="text">Finished</div>
      </div>

      <div v-for="(task) in category.task" :key="task.id" class="task">
        <div class="task-name">{{ task.name }}</div>
        <div class="task-desc">{{ task.taskDesc }}</div>
        <div class="task-date">Due Date: {{ $formatTime(task.dueTime) }}</div>
        <div class="task-estimatedTime">Estimated time: {{ task.estimatedTime }}</div>
        <div v-if="task.priorityRating == 1" class="rating rating-1">!</div>
        <div v-if="task.priorityRating == 2" class="rating rating-2">!!</div>
        <div v-if="task.priorityRating == 3" class="rating rating-3">!!!</div>
      </div>
    </div>

    <i @click="categoryVisible = true" class="el-icon-circle-plus"></i>

    <el-dialog
      width="461px"
      title="Add New Category"
      :visible.sync="categoryVisible">
      <el-input v-model="categoryName" placeholder="Category name*"></el-input>
      <p>Choose a color</p>
      <div class="color-list">
        <div v-for="(color, index) in colorList" :key="index" class="color-item" :style="{ background: color }" @click="currentColor = index">
          <i v-if="currentColor == index" class="el-icon-check"></i>
        </div>
      </div>
      <el-button @click="createCategory" style="width: 100%;background: rgb(98, 84, 234);color: #fff;margin: 16px 0 0;">Create category</el-button>
    </el-dialog>

    <el-dialog
      width="461px"
      title="Add New Task"
      :visible.sync="taskVisible">
      <el-input v-model="taskTemp.name" placeholder="Task name*" style="margin: 0 0 8px"></el-input>
      <el-input v-model="taskTemp.taskDesc" type="textarea" placeholder="Type the description here..." style="margin: 0 0 8px"></el-input>
      <!-- <el-date-picker v-model="taskTemp.date" type="date" placeholder="Date" style="margin: 0 0 8px"></el-date-picker> -->
      <el-date-picker v-model="taskTemp.dueTime" type="datetime" placeholder="Due time" style="margin: 0 0 8px"></el-date-picker>
      <el-input v-model="taskTemp.estimatedTime" type="datetime" placeholder="Estimated time to complete" style="margin: 0 0 8px"></el-input>
      <div style="margin: 0 0 8px">Priority rating</div>
      <div class="rating-list">
        <div :class="['rating', taskTemp.priorityRating == 1 && 'active']" @click="taskTemp.priorityRating = 1">!</div>
        <div :class="['rating', taskTemp.priorityRating == 2 && 'active']" @click="taskTemp.priorityRating = 2">!!</div>
        <div :class="['rating', taskTemp.priorityRating == 3 && 'active']" @click="taskTemp.priorityRating = 3">!!!</div>
      </div>
      <el-button @click="createTask" style="width: 100%;background: rgb(98, 84, 234);color: #fff;margin: 16px 0 0;">Create task</el-button>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      categoryList: [],
      categoryVisible: false,
      categoryName: '',
      categoryColor: '',
      currentColor: 0,
      colorList: ['rgb(251,228,241)', 'rgb(250,201,227)', 'rgb(249,229,254)', 'rgb(242,202,252)', 'rgb(234,230,253)', 'rgb(215,209,252)', 'rgb(219,240,253)', 'rgb(177,227,251)', 'rgb(198,250,239)', 'rgb(144,244,225)'],
      taskVisible: false,
      taskTemp: {
        name: '',
        taskDesc: '',
        // date: '',
        dueTime: '',
        estimatedTime: '',
        priorityRating: 1,
        status: 'unfinished',
        categoryId: 0
      }
    }
  },
  created () {
    this.fetchCategoryList()
  },
  methods: {
    async createCategory () {
      await axios.post(`${this.$host}/category`, {
        name: this.categoryName,
        color: this.colorList[this.currentColor]
      })
      this.categoryVisible = false
      this.categoryName = ''
      this.categoryColor = ''
      this.currentColor = 0
      this.fetchCategoryList()
    },
    async fetchCategoryList () {
      const { data } = await axios.get(`${this.$host}/category`)
      this.categoryList = data
      const res = await axios.get(`${this.$host}/task`)
      res.data.forEach((item, index) => {
        const cateInx = this.categoryList.findIndex(_ => _.id === item.categoryId)
        this.categoryList[cateInx].task = this.categoryList[cateInx].task || []
        this.categoryList[cateInx].task.push(item)
      })
      this.$forceUpdate()
    },
    async createTask () {
      await axios.post(`${this.$host}/task`, {
        ...this.taskTemp,
        date: '',
        dueTime: +this.taskTemp.dueTime
      })
      this.taskVisible = false
      this.taskTemp = {
        name: '',
        taskDesc: '',
        // date: '',
        dueTime: '',
        estimatedTime: '',
        priorityRating: 1,
        status: 'unfinished',
        categoryId: 0
      }
      this.fetchCategoryList()
    }
  }
}
</script>

<style lang="scss" scoped>
.kanban {
  position: relative;
  display: flex;
  height: 100%;
  padding: 16px;
  overflow-x: auto;
  .category {
    width: 250px;
    margin: 0 16px 0 0;
    background: rgb(228, 229, 251);
    border-radius: 8px;
    padding: 8px;
    font-size: 12px;
    overflow-y: auto;
    .head {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .name {
      display: inline-block;
      border-radius: 100px;
      padding: 0 8px;
    }
    .switch {
      display: flex;
      align-items: center;
      margin: 16px 0;
      .text {
        margin: 0 8px;
      }
    }
    .task {
      padding: 8px;
      border-radius: 8px;
      background: #fff;
      margin: 0 0 8px;
      .task-desc {
        margin: 0 0 8px;
      }
      .task-desc,
      .task-date,
      .task-estimatedTime {
        color: #888888;
      }
      .rating {
        display: inline-block;
        border-radius: 4px;
        text-align: center;
        width: 30px;
        color: #fff;
        margin: 8px 0 0;
      }
      .rating-1 {
        background: rgb(45, 59, 228);
      }
      .rating-2 {
        background: rgb(122, 125, 237);
      }
      .rating-3 {
        background: rgb(199, 200, 248);
      }
    }
  }
  .el-icon-circle-plus {
    position: absolute;
    right: 32px;
    top: 50%;
    transform: translateY(-50%);
    color: rgb(153, 155, 242);
    font-size: 80px;
    cursor: pointer;
  }
  .color-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    .color-item {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 18%;
      height: 75px;
      border-radius: 50%;
      margin: 0 0 8px;
      cursor: pointer;
      font-size: 32px;
      color: #fff;
    }
  }
  .rating-list {
    display: flex;
    .rating {
      border-radius: 4px;
      text-align: center;
      width: 30px;
      color: #fff;
      margin: 0 8px 0 0;
      background: rgb(222, 222, 222);
      cursor: pointer;
      &.active {
        background: rgb(45, 59, 228);
      }
    }
  }
}
</style>
