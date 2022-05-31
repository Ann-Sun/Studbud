<template>
  <div class="pomodoro">
    <div class="head">
      <img src="@/assets/reset.png" alt="">
      <el-dropdown :hide-on-click="false" trigger="click">
        <img src="@/assets/setting.png" alt="">
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="timerVisible = true">Timer</el-dropdown-item>
          <el-dropdown-item @click.native="musicVisible = true">Music</el-dropdown-item>
          <el-dropdown-item>Reset to default</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <img src="@/assets/full.png" alt="" @click="full">
    </div>
    <div class="bg">
      <div class="block">
        <div class="left">
          <div class="content"></div>
        </div>
        <div class="right">
          <div class="content"></div>
        </div>
      </div>
      <div class="timer">{{ showTime }}</div>
    </div>

    <div class="ctrl">
      <el-switch v-model="stopwatchEnabel" active-color="#4e42c7"></el-switch>
      <span>Stopwatch Timer</span>
    </div>
    <div class="ctrl">
      <el-switch v-model="pomodoroEnabel" active-color="#4e42c7"></el-switch>
      <span>Pomodoro Timer</span>
    </div>
    <div class="ctrl">
      <el-switch v-model="musicEnabel" active-color="#4e42c7"></el-switch>
      <span>Music&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
    </div>

    <div class="btn" @click="handleStart">
      <img src="@/assets/play.png" alt="">
    </div>
    <el-dialog
      width="461px"
      title="Timer"
      :visible.sync="timerVisible">
      <div class="form-item">
        <label for="">Pomodoro</label>
        <el-select size="mini" v-model="pomodoro">
          <el-option
            v-for="item in options"
            :key="item"
            :value="item"
            :label="item">
          </el-option>
        </el-select>
        <span>MINUTES</span>
      </div>
      <div class="form-item">
        <label for="">Short Break</label>
        <el-select size="mini" v-model="shortBreak">
          <el-option
            v-for="item in options"
            :value="item"
            :key="item"
            :label="item">
          </el-option>
        </el-select>
        <span>MINUTES</span>
      </div>
      <div class="form-item">
        <label for="">Long Break</label>
        <el-select size="mini" v-model="longBreak">
          <el-option
            v-for="item in options"
            :value="item"
            :key="item"
            :label="item">
          </el-option>
        </el-select>
        <span>MINUTES</span>
      </div>
      <div class="form-item">
        <label for="">Loop</label>
        <el-select size="mini" v-model="loop">
          <el-option
            v-for="item in options"
            :value="item"
            :key="item"
            :label="item">
          </el-option>
        </el-select>
      </div>
    </el-dialog>
    <el-dialog
      width="461px"
      title="Music"
      :visible.sync="musicVisible">
      <div class="form-item">
        <label style="width: 50px" for="">Music</label>
        <el-select size="mini" v-model="currentMusic">
          <el-option
            v-for="item in musicList"
            :key="item.name"
            :value="item.name"
            :label="item.name">
          </el-option>
        </el-select>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      timerVisible: false,
      pomodoro: 25,
      shortBreak: 5,
      longBreak: 20,
      loop: 4,
      options: Array(60).fill(1).map((item, index) => {
        return index + 1
      }),
      musicVisible: false,
      currentMusic: '3462',
      musicList: [
        {
          name: '3462',
          path: require('@/assets/3462.mp3')
        },
        {
          name: '10152',
          path: require('@/assets/10152.mp3')
        }
      ],
      audio: null,
      stopwatchEnabel: false,
      pomodoroEnabel: true,
      musicEnabel: true,
      showTime: '',
      step: 1
    }
  },
  watch: {
    currentMusic () {
      this.play()
    },
    musicVisible () {
      this.stop()
    },
    pomodoro () {
      this.reset()
    },
    shortBreak () {
      this.reset()
    }
  },
  created () {
    this.reset()
  },
  methods: {
    full () {
      const target = document.body
      if (target.requestFullscreen) {
        target.requestFullscreen()
      }
      if (target.webkitRequestFullscreen) {
        target.webkitRequestFullscreen()
      }
      if (target.mozRequestFullScreen) {
        target.mozRequestFullScreen()
      }
      if (target.msRequestFullscreen) {
        target.msRequestFullscreen()
      }
    },
    play () {
      if (!this.musicEnabel) {
        return
      }
      this.stop()
      const music = this.musicList.find(item => item.name === this.currentMusic)
      this.audio = new Audio()
      const audio = this.audio
      audio.src = music.path
      audio.play()
    },
    stop () {
      if (this.audio) {
        this.audio.pause()
      }
    },
    reset () {
      this.step = 1
      this.showTimer = new this.$timer(this.pomodoro * 60, false)
      this.showTime = this.showTimer.formatOutput()
    },
    handleStart () {
      if (this.step === 1) {
        this.showTimer = new this.$timer(this.pomodoro * 60, false)
      } else if (this.step === 2) {
        this.showTimer = new this.$timer(this.shortBreak * 60, false)
      } else if (this.step === 3) {
        this.showTimer = new this.$timer(this.longBreak * 60, false)
      }
      this.showTimer.start((res) => {
        this.showTime = res
        if (res === '00:00:00' && this.step === 1) {
          this.step = 2
          this.handleStart()
          this.play()
        } else if (res === '00:00:00' && this.step === 2) {
          this.step = 1
          this.handleStart()
          this.play()
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.pomodoro {
  background: #fff;
  height: 100%;
  .head  {
    display: flex;
    justify-content: flex-end;
    padding: 16px;
    img {
      width: 24px;
      height: 24px;
      margin: 0 0 0 8px;
      cursor: pointer;
    }
  }
  .bg {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 400px;
    height: 400px;
    background: rgb(166, 164, 241);
    border-radius: 50%;
    margin: 0 auto 56px;
    .block {
      width: 350px;
      height: 350px;
      display: flex;
      .left, .right {
        position: relative;
        width: 50%;
        overflow: hidden;
        .content {
          width: 350px;
          height: 350px;
          border: 8px solid #fff;
          border-radius: 50%;
        }
      }
      .right {
        .content {
          position: absolute;
          top: 0;
          right: 0;
        }
      }
    }
    .timer {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      font-size: 32px;
      color: #fff;
    }
  }
  .ctrl {
    display: flex;
    justify-content: center;
    margin: 0 0 8px;
    span {
      margin: 0 0 0 8px;
    }
  }
  .btn {
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100px;
    height: 100px;
    border-radius: 50%;
    background: rgb(166, 164, 241);
    margin: 56px auto 0;
  }
  .form-item {
    display: flex;
    align-items: center;
    margin: 0 0 8px;
    label {
      width: 100px;
    }
    .el-select {
      width: 80px;
    }
    span {
      margin: 0 0 0 8px;
    }
  }
}
</style>
