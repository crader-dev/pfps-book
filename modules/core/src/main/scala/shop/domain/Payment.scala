package shop.domain

import io.estatico.newtype.macros.newtype
import squants.market.Money

import java.util.UUID

trait PaymentClient[F[_]] {
  def process(payment: Payment): F[PaymentId]
}

@newtype case class PaymentId(uuid: UUID)

case class Payment(
  id: UserId,
  total: Money,
  card: Card
)

case class Card() //TODO: define & move? (Ch.5)
