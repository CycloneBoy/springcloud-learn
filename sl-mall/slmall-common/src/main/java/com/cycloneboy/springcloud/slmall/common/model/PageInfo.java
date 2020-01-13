package com.cycloneboy.springcloud.slmall.common.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import lombok.Data;

/**
 * Create by  sl on 2020-01-12 17:46
 */
@Data
public class PageInfo<T> implements Serializable {

  private static final long serialVersionUID = -2040670801987807850L;
  private int pageNum;
  private int pageSize;
  private int size;
  private String orderBy;
  private int startRow;
  private int endRow;
  private long total;
  private int pages;
  private List<T> list;
  private int firstPage;
  private int prePage;
  private int nextPage;
  private int lastPage;
  private boolean isFirstPage;
  private boolean isLastPage;
  private boolean hasPreviousPage;
  private boolean hasNextPage;
  private int navigatePages;
  private int[] navigatepageNums;

  public PageInfo() {
    this.isFirstPage = false;
    this.isLastPage = false;
    this.hasPreviousPage = false;
    this.hasNextPage = false;
  }

  public PageInfo(List<T> list) {
    this(list, 8);
  }

  public PageInfo(List<T> list, int navigatePages) {
    this.isFirstPage = false;
    this.isLastPage = false;
    this.hasPreviousPage = false;
    this.hasNextPage = false;
    if (list instanceof Collection) {
      this.pageNum = 1;
      this.pageSize = list.size();
      this.pages = 1;
      this.list = list;
      this.size = list.size();
      this.total = (long) list.size();
      this.startRow = 0;
      this.endRow = list.size() > 0 ? list.size() - 1 : 0;
    }

    if (list instanceof Collection) {
      this.navigatePages = navigatePages;
      this.calcNavigatepageNums();
      this.calcPage();
      this.judgePageBoudary();
    }

  }

  private void calcNavigatepageNums() {
    int i;
    if (this.pages <= this.navigatePages) {
      this.navigatepageNums = new int[this.pages];

      for (i = 0; i < this.pages; ++i) {
        this.navigatepageNums[i] = i + 1;
      }
    } else {
      this.navigatepageNums = new int[this.navigatePages];
      i = this.pageNum - this.navigatePages / 2;
      int endNum = this.pageNum + this.navigatePages / 2;
      if (i < 1) {
        i = 1;

        for (i = 0; i < this.navigatePages; ++i) {
          this.navigatepageNums[i] = i++;
        }
      } else if (endNum > this.pages) {
        endNum = this.pages;

        for (i = this.navigatePages - 1; i >= 0; --i) {
          this.navigatepageNums[i] = endNum--;
        }
      } else {
        for (i = 0; i < this.navigatePages; ++i) {
          this.navigatepageNums[i] = i++;
        }
      }
    }

  }

  private void calcPage() {
    if (this.navigatepageNums != null && this.navigatepageNums.length > 0) {
      this.firstPage = this.navigatepageNums[0];
      this.lastPage = this.navigatepageNums[this.navigatepageNums.length - 1];
      if (this.pageNum > 1) {
        this.prePage = this.pageNum - 1;
      }

      if (this.pageNum < this.pages) {
        this.nextPage = this.pageNum + 1;
      }
    }

  }

  private void judgePageBoudary() {
    this.isFirstPage = this.pageNum == 1;
    this.isLastPage = this.pageNum == this.pages;
    this.hasPreviousPage = this.pageNum > 1;
    this.hasNextPage = this.pageNum < this.pages;
  }
}
