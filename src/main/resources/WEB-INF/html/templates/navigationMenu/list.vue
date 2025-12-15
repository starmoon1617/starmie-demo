<template>
    <v-layout>
        <v-container>
            <v-row>
                <v-col cols="1" sm="6" md="1"><v-btn outlined color="primary" dark class="mb-2" @click="search">查找</v-btn></v-col>
                <v-col cols="1" sm="6" md="1"><v-btn outlined color="primary" dark class="mb-2" @click="clear">清除</v-btn></v-col>
                <v-col cols="1" sm="6" md="1"><v-btn outlined color="primary" dark class="mb-2" @click="create">新增</v-btn></v-col>
                <v-col cols="1" sm="6" md="1"><v-btn outlined color="primary" dark class="mb-2" @click="exportDatas">导出</v-btn><a href="#" hidden="true" style="display:none" id="downLoadFileHref" ref="downLoadFileHref"></a></v-col>
                <v-col cols="1" sm="6" md="1"><v-btn outlined color="primary" dark class="mb-2" @click="importData">导入</v-btn></v-col>
            </v-row>
        </v-container>
    </v-layout>
    <v-layout>
        <v-container>
            <v-data-table :headers="tableDatas.headers" :items="tableDatas.dataItems" :server-items-length="tableDatas.total" class="elevation-1"
                :footer-props="footOptions" :options.sync="pagination">
                <template v-slot:top>
                    <v-toolbar flat color="white" height="1px">
                        <v-dialog v-model="dialog" max-width="500px">
                            <v-card>
                                <v-card-title>
                                    <span class="headline">{{ formTitle }}</span>
                                </v-card-title>
                                <v-card-text>
                                    <v-container>
                                    
                                    <v-row>
                                        <v-col cols="1" sm="12" md="12">
                                            <template v-if="editAble">
                                                <v-text-field v-model="editedItem.menuNo" label="菜单编码" outlined></v-text-field>
                                            </template>
                                            <template v-else>
                                                <v-text-field v-model="editedItem.menuNo" label="菜单编码" outlined readOnly="true"></v-text-field>
                                            </template>
                                            </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="1" sm="12" md="12">
                                            <template v-if="editAble">
                                                <v-text-field v-model="editedItem.menuName" label="菜单名称" outlined></v-text-field>
                                            </template>
                                            <template v-else>
                                                <v-text-field v-model="editedItem.menuName" label="菜单名称" outlined readOnly="true"></v-text-field>
                                            </template>
                                            </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="1" sm="12" md="12">
                                            <template v-if="editAble">
                                                <v-text-field v-model="editedItem.uri" label="菜单资源地址" outlined></v-text-field>
                                            </template>
                                            <template v-else>
                                                <v-text-field v-model="editedItem.uri" label="菜单资源地址" outlined readOnly="true"></v-text-field>
                                            </template>
                                            </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="1" sm="12" md="12">
                                            <template v-if="editAble">
                                                <v-text-field v-model="editedItem.moduleNo" label="模块编码" outlined></v-text-field>
                                            </template>
                                            <template v-else>
                                                <v-text-field v-model="editedItem.moduleNo" label="模块编码" outlined readOnly="true"></v-text-field>
                                            </template>
                                            </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="1" sm="12" md="12">
                                            <template v-if="editAble">
                                                <v-text-field v-model="editedItem.createTime" label="创建时间" outlined></v-text-field>
                                            </template>
                                            <template v-else>
                                                <v-text-field v-model="editedItem.createTime" label="创建时间" outlined readOnly="true"></v-text-field>
                                            </template>
                                            </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="1" sm="12" md="12">
                                            <template v-if="editAble">
                                                <v-text-field v-model="editedItem.createBy" label="创建用户" outlined></v-text-field>
                                            </template>
                                            <template v-else>
                                                <v-text-field v-model="editedItem.createBy" label="创建用户" outlined readOnly="true"></v-text-field>
                                            </template>
                                            </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="1" sm="12" md="12">
                                            <template v-if="editAble">
                                                <v-text-field v-model="editedItem.updateTime" label="更新时间" outlined></v-text-field>
                                            </template>
                                            <template v-else>
                                                <v-text-field v-model="editedItem.updateTime" label="更新时间" outlined readOnly="true"></v-text-field>
                                            </template>
                                            </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="1" sm="12" md="12">
                                            <template v-if="editAble">
                                                <v-text-field v-model="editedItem.updateBy" label="更新用户" outlined></v-text-field>
                                            </template>
                                            <template v-else>
                                                <v-text-field v-model="editedItem.updateBy" label="更新用户" outlined readOnly="true"></v-text-field>
                                            </template>
                                            </v-col>
                                    </v-row>
                                    
                                    </v-container>
                                </v-card-text>
                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <template v-if="editAble">
                                        <v-btn color="blue darken-1" text @click="close">取消</v-btn>
                                        <v-btn color="blue darken-1" text @click="save">保存</v-btn>
                                    </template>
                                    <template v-else>
                                        <v-btn color="blue darken-1" text @click="close">确定</v-btn>
                                    </template>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        <v-dialog v-model="deleteDialog" max-width="500px">
                            <v-card>
                                <v-card-title>
                                    <span class="headline">{{ deleteFormTitle }}</span>
                                </v-card-title>
                                <v-card-text> {{deleteMsg}} </v-card-text>
                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn color="blue darken-1" text @click="cancelDelete">取消</v-btn>
                                    <v-btn color="blue darken-1" text @click="confirmDelete">删除</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        <v-dialog v-model="confirmDialog" max-width="500px">
                            <v-card>
                                <v-card-title>
                                    <span class="headline">{{ confirmFormTitle }}</span>
                                </v-card-title>
                                <v-card-text> {{confirmMsg}} </v-card-text>
                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn color="blue darken-1" text @click="colseConfirm">确定</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        <v-dialog v-model="importDialog" max-width="500px">
                            <v-card>
                                <v-card-title>
                                    <span class="headline">导入</span>
                                </v-card-title>
                                <v-card-text>
                                    <v-file-input id="importFile" placeholder="请选择要上传的文件" outlined v-model="importFile"></v-file-input>
                                </v-card-text>
                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn color="blue darken-1" text @click="importDatas">导入</v-btn>
                                    <v-btn color="blue darken-1" text @click="closeImport">取消</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </v-toolbar>
                </template>
                <template v-slot:item.actions="{ item }">
                    <v-icon class="mr-2" @click="viewItem(item)">mdi-text-box-outline</v-icon>
                    <v-icon class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
                    <v-icon class="mr-2" @click="deleteItem(item)">mdi-delete</v-icon>
                </template>
            </v-data-table>
        </v-container>
    </v-layout>
    </template>
    <script type="text/javascript" th:inline="javascript">
        new Vue({
            el: '#app',
            vuetify: new Vuetify(),
            data() {
                return {
                    dialog : false,
                    formTitle : "新增",
                    deleteDialog : false,
                    deleteFormTitle : "确认",
                    deleteMsg : "",
                    confirmDialog : false,
                    confirmFormTitle : "",
                    confirmMsg : "",
                    editAble : false,
                    importFile : null,
                    importDialog : false,
                    editedItem: {
                        id:'',
                        menuNo:'', 
                        menuName:'', 
                        uri:'', 
                        moduleNo:'', 
                        createTime:'', 
                        createBy:'', 
                        updateTime:'', 
                        updateBy:''
                    },
                    searchForm: {
                        id:'',
                        menuNo:'', 
                        menuName:'', 
                        uri:'', 
                        moduleNo:'', 
                        createTime:'', 
                        createBy:'', 
                        updateTime:'', 
                        updateBy:''
                    },
                    footOptions: {
                        itemsPerPageOptions : [10,20,50]
                    },
                    pagination : {
                        itemsPerPage : 20,
                        page : 1,
                        sortBy : [],
                        sortDesc : [],
                        groupBy : [],
                        groupDesc : [],
                        mustSort : false,
                        multiSort : false
                    },
                    tableDatas: {
                        headers: [ 
                            {text: '主键', value: 'id', width:100, sortable: false},
                            {text: '菜单编码', value: 'menuNo', width:100, sortable: false}, 
                            {text: '菜单名称', value: 'menuName', width:100, sortable: false}, 
                            {text: '菜单资源地址', value: 'uri', width:100, sortable: false}, 
                            {text: '模块编码', value: 'moduleNo', width:100, sortable: false}, 
                            {text: '创建时间', value: 'createTime', width:100, sortable: false}, 
                            {text: '创建用户', value: 'createBy', width:100, sortable: false}, 
                            {text: '更新时间', value: 'updateTime', width:100, sortable: false}, 
                            {text: '更新用户', value: 'updateBy', width:100, sortable: false}
                            
                        ],
                        dataItems: [],
                        total : 0,
                        exportExclude : ['id', 'actions'],
                        exportFileName : "NavigationMenu导出"
                    }
                }
            },
            watch: {
                pagination: {
                  handler () {
                      this.loadDatas();
                  },
                  deep: true,
                },
            },
            methods: {
                loadDatas() {
                    let _this = this;
                    common.searchDatas(/*[[@{/excel/list}]]*/'/navigation_menu/list', _this.searchForm, _this.pagination, function(response){
                        let resp = response.data;
                        if(!resp.code && resp.data) {
                            _this.tableDatas.total = resp.data.total;
                            if(resp.data.total > 0) {
                                _this.tableDatas.dataItems = resp.data.datas;
                            } else {
                                _this.tableDatas.dataItems = [];
                            }
                        } else {
                            console.log(resp.msg);
                        }
                    }, function(error){
                        console.log(error);
                    });
                },
                search() {
                    this.loadDatas();
                },
                clear() {
                    let isDoClear = false;
                    for(let p in this.searchForm) {
                        if (this.searchForm[p] !== "") {
                            this.searchForm[p] = "";
                            isDoClear = true;
                        }
                    }
                    if(isDoClear) {
                        this.search();
                    }
                },
                create() {
                    this.editAble = true; 
                    this.formTitle = "新增";
                    this.editedItem = Object.assign({}, {
                        id:'',
                        menuNo:'', 
                        menuName:'', 
                        uri:'', 
                        moduleNo:'', 
                        createTime:'', 
                        createBy:'', 
                        updateTime:'', 
                        updateBy:''
                    });
                    this.dialog = true;
                }, 
                viewItem (item) {
                    this.editAble = false;
                    this.formTitle = "查看";
                    this.editedItem = Object.assign({}, item);
                    this.dialog = true;
                },
                editItem (item) {
                    this.editAble = true;
                    this.formTitle = "编辑";
                    this.editedItem = Object.assign({}, item);
                    this.dialog = true;
                },
                deleteItem (item) {
                    this.editedItem = Object.assign({}, item);
                    this.deleteMsg = "确定要删除 " + item.name + "(" + item.email + ")";
                    this.deleteDialog = true;
                },
                close () {
                    this.dialog = false
                },
                confirmDelete() {
                    this.deleteData(this.editedItem);
                    this.deleteDialog = false;
                },
                cancelDelete() {
                    this.deleteDialog = false
                    this.editedItem = Object.assign({}, {
                        id:'',
                        menuNo:'', 
                        menuName:'', 
                        uri:'', 
                        moduleNo:'', 
                        createTime:'', 
                        createBy:'', 
                        updateTime:'', 
                        updateBy:''
                    });
                },
                save () {
                    this.saveData(this.editedItem);
                    this.close();
                },
                colseConfirm() {
                    this.confirmDialog = false;
                },
                saveData(item) {
                    let _this = this;
                    let url = /*[[@{/navigation_menu/save}]]*/'/navigation_menu/save'; 
                    if (item.id) {
                        url = /*[[@{/navigation_menu/update}]]*/'/navigation_menu/update'; 
                    }
                    common.updateData(url, item, function(response){
                        let resp = response.data;
                        if(resp.success && resp.data) {
                            _this.confirmFormTitle = "成功";
                            _this.confirmMsg = "保存成功";
                        } else {
                            _this.confirmFormTitle = "失败";
                            _this.confirmMsg = "保存失败";
                        }
                        _this.confirmDialog = true;
                    }, function(error){
                        _this.confirmFormTitle = "失败";
                        _this.confirmMsg = "异常:" + error;
                        _this.confirmDialog = true;
                    });
                },
                deleteData(item) {
                    let _this = this;
                    common.updateData(/*[[@{/navigation_menu/delete}]]*/'/navigation_menu/delete', item, function(response){
                        let resp = response.data;
                        if(resp.success && resp.data) {
                            _this.confirmFormTitle = "成功";
                            _this.confirmMsg = "删除成功";
                        } else {
                            _this.confirmFormTitle = "失败";
                            _this.confirmMsg = "删除失败";
                        }
                        _this.confirmDialog = true;
                    }, function(error){
                        _this.confirmFormTitle = "失败";
                        _this.confirmMsg = "异常:" + error;
                        _this.confirmDialog = true;
                    });
                },
                exportDatas() {
                    let _this = this;
                    common.exportDatas(/*[[@{/navigation_menu/export}]]*/'/navigation_menu/export', _this.searchForm, _this.exportFileName, _this.tableDatas.headers, function(error){
                        _this.confirmFormTitle = "失败";
                        _this.confirmMsg = "异常:" + error;
                        _this.confirmDialog = true;
                    });
                },
                importData () {
                    this.importFile = null;
                    this.importDialog = true;
                },
                importDatas () {
                    let _this = this;
                    common.importDatas(/*[[@{/navigation_menu/import}]]*/'/navigation_menu/import', _this.importFile, 
                    "id,menuNo,menuName,uri,moduleNo,createTime,createBy,updateTime,updateBy", 
                    function(response){
                        let resp = response.data;
                        if(resp.success && resp.data) {
                            _this.confirmFormTitle = "成功";
                            _this.confirmMsg = "导入成功";
                        } else {
                            _this.confirmFormTitle = "失败";
                            _this.confirmMsg = "导入失败," + resp.errorMsg;
                        }
                        _this.closeImport();
                        _this.confirmDialog = true;
                    }, 
                    function(error){
                        _this.confirmFormTitle = "失败";
                        _this.confirmMsg = "异常:" + error;
                        _this.closeImport();
                        _this.confirmDialog = true;
                    });
                },
                closeImport() {
                    this.importDialog = false;
                    this.importFile = null;
                }
            }
        });
    </script>