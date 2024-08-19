<template>
    <Paginator
      :rows="rows"
      :totalRecords="totalRecords"
      :rowsPerPageOptions="[12,24,36,42]"
      @page="onPageChange"
      :first="first"
      class="mb-4"
    />
  </template>
  
  <script>
  import Paginator from "primevue/paginator";
  
  export default {
    nombre: 'InmueblePaginator',
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