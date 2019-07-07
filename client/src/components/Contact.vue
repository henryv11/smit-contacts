<template>
  <div class="rounded shadow-sm border">
    <b-modal
      v-model="modal"
      size="sm"
      centered
      title="Kindel, et soovid kontakti kustutada?"
      header-bg-variant="danger"
      header-text-variant="light"
      hide-footer
    >
      <div class="d-flex w-100 justify-content-between px-3">
        <b-button @click="deleteContact" variant="outline-dark" size="sm">
          <font-awesome-icon icon="trash-alt" class="mr-2"></font-awesome-icon>Kustuta
        </b-button>
        <b-button @click="hideDeleteConfirm" variant="outline-danger" size="sm">
          <font-awesome-icon icon="ban" class="mr-2"></font-awesome-icon>Tühista
        </b-button>
      </div>
    </b-modal>
    <div v-if="!editMode" style="min-height: 5.5rem">
      <div class="d-flex w-100 justify-content-between bg-light px-2 py-1 border-bottom">
        <p class="h5 mb-0">
          <font-awesome-icon icon="user" size="xs" class="mr-1 pb-1"></font-awesome-icon>
          {{contact.name}}
        </p>
        <div>
          <b-button
            variant="link text-dark"
            size="sm"
            class="p-0 mr-1"
            @click="editMode = !editMode"
          >
            <font-awesome-icon icon="edit"></font-awesome-icon>
          </b-button>
        </div>
      </div>

      <div class="text-left px-2 py-1">
        <p class="m-0 text-muted">{{contact.codename}}</p>

        <p class="small m-0 text-muted">{{contact.phoneNumber}}</p>
      </div>
    </div>
    <div v-else-if="editMode" style="min-height: 5.5rem">
      <div class="d-flex w-100 justify-content-between bg-dark px-2 py-1 border-bottom">
        <div class="text-white">
          <b-form-input
            v-model="editForm.name"
            size="sm"
            :placeholder="contact.name"
            style="height: 1.5rem"
            class="d-inline-block my-0 py-2 border-left-0 border-right-0 border-top-0 rounded-0 bg-dark text-white"
          ></b-form-input>
        </div>
        <div>
          <b-button
            variant="link text-danger"
            size="sm"
            class="p-0 mr-2"
            @click="showDeleteConfirm"
          >
            <font-awesome-icon icon="trash-alt"></font-awesome-icon>
          </b-button>

          <b-button variant="link text-white" size="sm" class="p-0 mr-2" @click="resetEditForm">
            <font-awesome-icon icon="undo"></font-awesome-icon>
          </b-button>

          <b-button variant="link text-white" size="sm" class="p-0 mr-2" @click="editContact">
            <font-awesome-icon icon="save"></font-awesome-icon>
          </b-button>

          <b-button
            variant="link text-white"
            size="sm"
            class="p-0 mr-1"
            @click="editMode = !editMode"
          >
            <font-awesome-icon icon="backspace"></font-awesome-icon>
          </b-button>
        </div>
      </div>

      <div class="text-left px-2 py-1">
        <b-form-input
          v-model="editForm.codename"
          size="sm"
          :placeholder="contact.codename"
          style="height: 1rem"
          class="my-1 py-2 w-50 border-left-0 border-right-0 border-top-0 rounded-0"
        ></b-form-input>

        <b-form-input
          v-model="editForm.phoneNumber"
          size="sm"
          type="tel"
          :placeholder="contact.phoneNumber"
          style="height: 1rem"
          class="my-1 py-2 w-50 border-left-0 border-right-0 border-top-0 rounded-0"
        ></b-form-input>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Contact",
  props: {
    contact: Object
  },
  data() {
    return {
      editForm: {
        name: this.contact.name,
        codename: this.contact.codename,
        phoneNumber: this.contact.phoneNumber
      },
      editMode: false,
      modal: false
    };
  },
  methods: {
    editContact() {
      this.$http({
        method: "post",
        url: this.$apiBaseUrl + "/api/contacts/edit",
        headers: { Authorization: this.$store.getters.bearerToken },
        data: {
          id: this.contact.id,
          name: this.editForm.name,
          codename: this.editForm.codename,
          phoneNumber: this.editForm.phoneNumber
        }
      })
        .then(resp => {
          if (resp.data.success) {
            this.contact.name = resp.data.contact.name;
            this.contact.codename = resp.data.contact.codename;
            this.contact.phoneNumber = resp.data.contact.phoneNumber;
            this.editMode = false;
          }
        })
        .catch(err => console.log(err));
    },
    deleteContact() {
      this.$http({
        method: "post",
        url: this.$apiBaseUrl + "/api/contacts/delete/" + this.contact.id,
        headers: { Authorization: this.$store.getters.bearerToken }
      })
        .then(resp => {
          if (resp.data.success) {
            this.$emit("deleteSuccess", resp.data.contact.id);
            this.hideDeleteConfirm();
          }
        })
        .catch(err => console.log(err));
    },
    resetEditForm() {
      this.editForm.name = this.contact.name;
      this.editForm.codename = this.contact.codename;
      this.editForm.phoneNumber = this.contact.phoneNumber;
    },
    showDeleteConfirm() {
      this.modal = true;
    },
    hideDeleteConfirm() {
      this.modal = false;
    }
  }
};
</script>

<style lang="less" scoped>
</style>



