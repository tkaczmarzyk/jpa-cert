/**
 * This file is part of jpa-cert application.
 *
 * Jpa-cert is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jpa-cert is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jpa-cert; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.kaczmarzyk.jpacert.dto;

import java.math.BigDecimal;

import net.kaczmarzyk.jpacert.domain.Branch;


public class SalaryStats {

	private Branch branch;
	private BigDecimal maxSalary;
	private BigDecimal minSalary;
	private BigDecimal avgSalary;
	
	
	public SalaryStats(Branch branch, BigDecimal maxSalary, BigDecimal minSalary, Double avgSalary) {
		this.branch = branch;
		this.maxSalary = maxSalary;
		this.minSalary = minSalary;
		this.avgSalary = BigDecimal.valueOf(avgSalary);
	}

	public Branch getBranch() {
		return branch;
	}

	public BigDecimal getMaxSalary() {
		return maxSalary;
	}

	public BigDecimal getMinSalary() {
		return minSalary;
	}

	public BigDecimal getAvgSalary() {
		return avgSalary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avgSalary == null) ? 0 : avgSalary.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result
				+ ((maxSalary == null) ? 0 : maxSalary.hashCode());
		result = prime * result
				+ ((minSalary == null) ? 0 : minSalary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaryStats other = (SalaryStats) obj;
		if (avgSalary == null) {
			if (other.avgSalary != null)
				return false;
		} else if (!avgSalary.equals(other.avgSalary))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (maxSalary == null) {
			if (other.maxSalary != null)
				return false;
		} else if (!maxSalary.equals(other.maxSalary))
			return false;
		if (minSalary == null) {
			if (other.minSalary != null)
				return false;
		} else if (!minSalary.equals(other.minSalary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SalaryStats [branch=" + branch.getId() + ", maxSalary=" + maxSalary
				+ ", minSalary=" + minSalary + ", avgSalary=" + avgSalary + "]";
	}

	
}
