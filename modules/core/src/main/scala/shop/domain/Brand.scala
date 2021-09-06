package shop.domain

import io.estatico.newtype.macros.newtype

import java.util.UUID

trait Brands[F[_]] {
  def findAll: F[List[Category]]
  def create(name: CategoryName): F[CategoryId]
}

@newtype case class BrandId(value: UUID)
@newtype case class BrandName(value: String)

case class Brand(uuid: CategoryId, name: CategoryName)
