<%--
  Created by IntelliJ IDEA.
  User: kapitoha
  Date: 14.06.15
  Time: 0:49
  To change this template use File | Settings | File Templates.
--%>
<div class="navbar navbar-default navbar-static-top" style="margin-bottom: 0" role="navigation" id="nav-bar">
  <a class="navbar-brand" id="logo-bar" href="index">
    Rising Sun
  </a>

  <div id="action-bar">
    <div class="btn-group" id="myDropdown">
      <form id="search_form" action="search"></form>
      <button type="button" class="btn btn-default dropdown-toggle fa fa-search"
              data-toggle="dropdown">
        Search <span class="caret"></span>
      </button>
      <ul class="dropdown-menu">
        <li>
          <label for="keyword">Keyword</label>
          <input form="search_form" type="text" name="keyword" id="keyword" class="form-control"
                 title="Enter some keyword or phrase">
        </li>
        <li>
          <label for="author">Author</label>
          <input form="search_form" type="text" name="author" class="form-control"
                 title="Enter author name">
        </li>
        <li>
          <label for="tag">Tags</label>
          <input form="search_form" type="text" name="tag" class="form-control"
                 title="Enter tags through the space or comma.">
        </li>
        <li class="divider"></li>
        <li><input form="search_form" name="composite" type="checkbox" checked
                   title="If checked, all field will be included into search compositly."> Composite Search
        </li>
        <li class="divider"></li>
        <li><input form="search_form" type="submit" value="Search"></li>
      </ul>

    </div>
    <button type="button"
            class="btn btn-${ page_tag eq 'first_page'? 'primary':'info' } fa fa-home"
            onclick="invoke('index')"> Home
    </button>
    <button type="button"
            class="btn btn-${ page_tag eq 'archive'? 'primary':'info' } fa fa-archive"
            onclick="invoke('archive')"> Archive
    </button>
  </div>
</div>
