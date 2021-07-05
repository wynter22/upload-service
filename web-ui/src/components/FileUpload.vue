<template>
  <div>
    <c-card body-wrapper>
      <c-card-header>
        <h2 class="text-kakaopay">Kakaopay Task</h2>
        <span class="font-weight-bold text-dark">이우영 (u0lee@kakao.com)</span>
      </c-card-header>
      <c-card-body>
        <c-row>
          <c-col sm="12">
            <vue-dropzone
              ref="fileDropzone"
              :useCustomSlot="true"
              id="dropzone"
              @vdropzone-sending="interruptRequestServer"
              @vdropzone-complete="completeUpload"
              :options="dropzoneOptions">
              <div class="dropzone-custom-content">
                <h3 class="dropzone-custom-title">
                  Drop files here click to upload.
                </h3>
                <span class="font-weight-normal">
                  (This is just a demo dropzone. Selected files are not actually uploaded.)
                </span>
              </div>
              <div class=""></div>
            </vue-dropzone>
          </c-col>
          <c-col sm="12">
            <c-progress-bar :value="progressRate"
                            color="success"
                            animated
                            show-value
                            show-percentage
                            style="height:20px;"
                            class="mt-1">
            </c-progress-bar>
          </c-col>
        </c-row>
      </c-card-body>
    </c-card>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'FileUpload',
  data() {
    return {
      dropzoneOptions: {
        url: '/api/file/upload',
        acceptedFiles: '.csv',
        maxFilesize: 1000,
        maxFiles: 1000,
        createImageThumbnails: false,
        uploadMultiple: false,
        parallelUploads: 1,
      },
      progressRate: 0,
      sseClients: {},
    };
  },
  methods: {
    interruptRequestServer(file) {
      const clientId = file.upload.uuid;
      this.createSse(clientId);
      this.connectSse(clientId);
    },
    completeUpload(res) {
      if ('error' !== res.status) {
        this.disconnectSse(res.upload.uuid);
      }
    },
    connectSse(id) {
      if (this.sseClients.hasOwnProperty(id)) {
        const sseClient = this.sseClients[id];
        sseClient.connect()
          .then(sse => setTimeout(() => sse.off(), 60000))
          .catch((err) => {
            console.log('[Error] SseClient( id=', id, ') has problem');
            console.error(err);
            this.disconnectSse();
          });

        this.progressRate = 0;
      }
    },
    disconnectSse(id) {
      if (this.sseClients.hasOwnProperty(id)) {
        const sseClient = this.sseClients[id];
        sseClient.disconnect();
        axios.get('/api/sse/unsubscribe?id=' + id);
        delete this.sseClients[id];
      }
    },
    createSse(id) {
      const sseClient = this.$sse.create({
        url: '/api/sse/subscribe?id=' + id,
        format: 'json',
        polyfill: true,
      });

      sseClient.on('error', (e) => {
        console.error('lost connection or failed to parse!', e);
      });

      sseClient.on('message', (res) => {
        const progressInfo = res.data;
        const progressRate = (progressInfo.progressCount / progressInfo.totalCount) * 100;

        this.progressRate = progressRate;
      });

      this.sseClients[id] = sseClient;
    }
  },
  mounted() {
    this.progressRate = 0;
  },
};
</script>

<style scoped lang="scss">
.vue-dropzone {
  border: 2px dashed #379ce3;
  border-radius: 6px;
}
</style>
