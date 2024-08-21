<template>
    <Paginator
      :rows="rows"
      :totalRecords="totalRecords"
      :rowsPerPageOptions="[10,20,30,40]"
      @page="onPageChange"
      :first="first"
      class="mb-4"
    />
  </template>
  
  <script>
  import Paginator from "primevue/paginator";
  
  export default {
    name: 'InmueblePaginator',
    components: {
      Paginator
    },
    props: {
      rows: {
        type: Number,
        required: true
      },
      totalRecords: {
        type: Number,
        required: true
      },
      first: {
        type: Number,
        required: true
      }
    },
    methods: {
      onPageChange(event) {
        this.$emit('page-change', event);
      }
    }
  }
  </script>