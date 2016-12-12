<!DOCTYPE html>
<html>
<body>
<head>
  <link rel="stylesheet" href="style_admin.css">
</head>
<div class="panel-container">
  <div data-bind="css: {collapsed: collapsedNav}" class="panel-controls">
    <ul>
      <li data-bind="click: collapse.bind($data)" class="nav-control"><a><i class="fa fa-bars"></i></a></li>
      <li data-bind="css: {active: displayTab() === 'users'}, click: changeTab.bind($data, 'users')"><a><i class="fa fa-users"></i><span class="label">Users</span><i class="fa fa-caret-left caret"></i></a></li>
      <li data-bind="css: {active: displayTab() === 'documents'}, click: changeTab.bind($data, 'documents')"><a><i class="fa fa-archive"></i><span class="label">Documents</span><i class="fa fa-caret-left caret">  </i></a></li>
      <li data-bind="css: {active: displayTab() === 'pins'}, click: changeTab.bind($data, 'pins')"><a><i class="fa fa-thumb-tack"></i><span class="label">Pinned Docs</span><i class="fa fa-caret-left caret"></i></a></li>
      <!-- li.nav-control
      a
        i.fa.fa-sign-out
      -->
    </ul>
  </div>
  <section data-bind="css: {collapsed: collapsedNav}" class="panel-content">
    <div class="main-wrapper">
      <div id="users" data-bind="visible: displayTab() === 'users'" class="widget"><span>Users</span></div>
      <div id="documents" data-bind="visible: displayTab() === 'documents'" class="widget">
        <div id="collections">
          <div class="collection">
            <div class="collection-info">
              <div class="title"><span>Collection Name</span></div>
              <div class="detail"><span>January 28, 2014</span></div>
            </div>
            <div class="collection-controls"></div>
          </div>
        </div>
      </div>
      <div id="pins" data-bind="visible: displayTab() === 'pins'" class="widget"><span>Pins</span></div>
    </div>
  </section>
</div>
<script src="admin_menu.js"></script>
</body>
</html>